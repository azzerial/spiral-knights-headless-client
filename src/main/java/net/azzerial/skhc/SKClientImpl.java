/*
 * Copyright 2025 Robin Mercier (azzerial)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.azzerial.skhc;

import com.samskivert.util.RunQueue;
import com.threerings.presents.client.Client;
import com.threerings.presents.client.LogonException;
import com.threerings.presents.data.AuthCodes;
import com.threerings.presents.net.Credentials;
import com.threerings.presents.net.UsernamePasswordCreds;
import com.threerings.presents.util.SecureUtil;
import net.azzerial.skhc.enums.ConnectionStatus;
import net.azzerial.skhc.events.EventManager;
import net.azzerial.skhc.services.Service;
import net.azzerial.skhc.services.ServiceManager;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;

@Internal
public final class SKClientImpl implements SKClient {

    private static final Logger log = LoggerFactory.getLogger("net.azzerial.skhc.SKClient");

    private static final PublicKey PUBLIC_KEY;
    private static final String HOSTNAME = "18.233.212.89";
    private static final int[] PORTS = { 47624 };
    private static final String VERSION = "20220124075521";

    static {
        final String key = "a5ed0dc3892b9472cfb668e236064e989e95945dad18f3d7e7d8e474d6e03de38bc044c3429b9ca649d" +
            "0881d601c0eb8ffebc3756f0503f73a8ca1760943ea0e8921ad6f8102026586db3133844bbadbcfcfc666d23982d768451" +
            "1fbf6cd8bb1d02a14270d0854098d16fe88f99c05825b0fe1b6fd497709106f2c418796aaf7aab7c92f26fcd9fbb3c43df" +
            "48075fed8dd931273a7b0a333c8de5967797874c1944aed65b47f0792b273a529ac22a2dce08dad04eeebeeff67c7bc99b" +
            "97682bff488038b28e24f4b5eea77ed966caede52f2c1ecf2b403110a9765daa81ddf718129a040823bead3a0bdca70ef6" +
            "d08f483757a6d3b6e01fbbcb32006b7872bcd#10001";
        final PublicKey rsaPublicKey = SecureUtil.stringToRSAPublicKey(key);

        if (SecureUtil.ciphersSupported(rsaPublicKey)) {
            PUBLIC_KEY = rsaPublicKey;
        } else {
            throw new ExceptionInInitializerError("Could not construct PUBLIC_KEY");
        }
    }

    private final Credentials credentials;
    private final EnumSet<Service> services;
    private final EventManager eventManager = new EventManager();
    private final ConnectionListener connectionListener = new ConnectionListener(this);

    private Client client;
    private ServiceManager serviceManager;
    private Exception connectionError;
    private ConnectionStatus connectionStatus = ConnectionStatus.DISCONNECTED;

    /* Constructors */

    SKClientImpl(@NotNull Credentials credentials, @NotNull EnumSet<Service> services) {
        this.credentials = credentials;
        this.services = services;
    }

    /* Getters & Setters */

    @Nullable
    @Internal
    public Client getClient() {
        return client;
    }

    @NotNull
    @Internal
    public EventManager getEventManager() {
        return eventManager;
    }

    @Nullable
    @Internal
    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    @Internal
    public void setConnectionError(@Nullable Exception cause) {
        this.connectionError = cause;
    }

    @NotNull
    @Override // SKClient
    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    @Internal
    public void setConnectionStatus(ConnectionStatus status) {
        this.connectionStatus = status;
    }

    @Override // SKClient
    public boolean isConnectionStatus(@Nullable ConnectionStatus status) {
        return this.connectionStatus == status;
    }

    /* Methods */

    @CheckReturnValue
    @Override // SKClient
    public boolean connect() throws LoginException {
        if (client == null) {
            createClient();
        }

        log.debug("Attempting to connect to the game server...");
        final boolean willLogon = client.logon();

        if (willLogon) {
            try {
                awaitConnectionStatus(ConnectionStatus.CONNECTED);
                this.serviceManager = new ServiceManager(this, services);
                serviceManager.subscribe();
                log.info("The client is now connected to the game server.");
            } catch (Exception e) {
                throwLoginException();
                e.printStackTrace();
            }
        } else {
            log.warn("The client was already connected!");
        }
        return willLogon;
    }

    @CheckReturnValue
    @Override // SKClient
    public boolean disconnect() {
        boolean loggedOff = false;

        log.debug("Disconnecting from the game server...");
        if (client != null) {
            loggedOff = client.logoff(false);
            try {
                awaitConnectionStatus(ConnectionStatus.DISCONNECTED);
                serviceManager.unsubscribe();
                this.client = null;
                this.serviceManager = null;
                log.info("The client is now disconnected from the game server.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.warn("The client was not connected!");
        }
        return loggedOff;
    }

    @NotNull
    @Override // SKClient
    public SKClient addEventListeners(@NotNull Object listener, @NotNull Object... listeners) {
        Objects.requireNonNull(listener, "Provided listener cannot be null");
        Arrays.stream(listeners)
            .forEach(it -> Objects.requireNonNull(it, "Provided listener cannot be null"));
        eventManager.register(listener);
        for (Object it : listeners) {
            eventManager.register(it);
        }
        return this;
    }

    @NotNull
    @Override // SKClient
    public SKClient addEventListeners(@NotNull Collection<Object> listeners) {
        Objects.requireNonNull(listeners, "Provided listeners collection cannot be null");
        listeners.forEach(it -> Objects.requireNonNull(it, "Provided listeners collection cannot contain null elements"));
        for (Object it : listeners) {
            eventManager.register(it);
        }
        return this;
    }

    @NotNull
    @Override // SKClient
    public SKClient removeEventListeners(@NotNull Object listener, @NotNull Object... listeners) {
        Objects.requireNonNull(listener, "Provided listener cannot be null");
        Arrays.stream(listeners)
            .forEach(it -> Objects.requireNonNull(it, "Provided listener cannot be null"));
        eventManager.unregister(listener);
        for (Object it : listeners) {
            eventManager.unregister(it);
        }
        return this;
    }

    @NotNull
    @Override // SKClient
    public SKClient removeEventListeners(@NotNull Collection<Object> listeners) {
        Objects.requireNonNull(listeners, "Provided listeners collection cannot be null");
        listeners.forEach(it -> Objects.requireNonNull(it, "Provided listeners collection cannot contain null elements"));
        for (Object it : listeners) {
            eventManager.unregister(it);
        }
        return this;
    }

    @NotNull
    @Override // Object
    public String toString() {
        return "{" +
            "username=" + ((UsernamePasswordCreds) credentials).getUsername() +
            ", connectionStatus=" + connectionStatus +
            ", connectionError=" + connectionError +
            ", registeredListeners=" + eventManager.getRegisteredListeners().size() +
            '}';
    }

    /* Internal */

    private void createClient() {
        log.debug("Creating the game client...");
        final Client client = new Client(credentials, RunQueue.AWT);

        client.setVersion(VERSION);
        client.setPublicKey(PUBLIC_KEY);
        client.setRequireSecureAuth(true);
        client.setServer(HOSTNAME, PORTS, PORTS);
        client.addClientObserver(connectionListener);
        this.client = client;
        log.debug("The game client has been created.");
    }

    private void awaitConnectionStatus(@NotNull ConnectionStatus status) throws InterruptedException {
        Objects.requireNonNull(status, "Provided status cannot be null");
        if (connectionStatus == ConnectionStatus.CONNECTED) {
            return;
        }
        while (!connectionStatus.isConnectionCycle() || connectionStatus.ordinal() != status.ordinal()) {
            if (connectionStatus == ConnectionStatus.DISCONNECTED && connectionError != null) {
                throw new IllegalStateException("Unexpected state: the connection got closed", connectionError);
            }
            Thread.sleep(50);
        }
    }

    private void throwLoginException() throws LoginException {
        if (connectionError != null && connectionError instanceof LogonException) {
            final String error = connectionError.getMessage();
            connectionError = null;
            String message = error;

            switch (error) {
                case AuthCodes.NO_SUCH_USER:
                    message = "Invalid username";
                    break;
                case AuthCodes.INVALID_PASSWORD:
                    message = "Invalid password";
                    break;
                case AuthCodes.SERVER_ERROR:
                    message = "Internal server error";
                    break;
                case AuthCodes.SERVER_UNAVAILABLE:
                    message = "Server unavailable";
                    break;
                case AuthCodes.FAILED_TO_SECURE:
                    message = "Could not secure connection";
                    break;
            }
            throw new LoginException(message);
        }
    }
}
