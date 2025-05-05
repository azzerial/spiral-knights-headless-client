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

import com.threerings.presents.client.Client;
import com.threerings.presents.client.ClientAdapter;
import com.threerings.presents.client.LogonException;
import net.azzerial.skhc.enums.ConnectionStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ConnectionListener extends ClientAdapter {

    private static final Logger log = LoggerFactory.getLogger("net.azzerial.skhc.ConnectionListener");

    private final SKClientImpl skClient;

    /* Constructors */

    ConnectionListener(@NotNull SKClientImpl skClient) {
        this.skClient = skClient;
    }

    /* Methods */

    @Override // ClientAdapter
    public void clientWillLogon(Client client) {
        skClient.setConnectionStatus(ConnectionStatus.PENDING);
        log.debug("Connection status set to: PENDING");
    }

    @Override // ClientAdapter
    public void clientDidLogon(Client client) {
        skClient.setConnectionStatus(ConnectionStatus.CONNECTED);
        log.debug("Connection status set to: CONNECTED");
    }

    @Override // ClientAdapter
    public void clientFailedToLogon(Client client, Exception cause) {
        if (cause instanceof LogonException && !((LogonException) cause).isStillInProgress()) {
            skClient.setConnectionStatus(ConnectionStatus.DISCONNECTED);
            log.debug("Connection status set to: DISCONNECTED");
            skClient.setConnectionError(cause);
        }
    }

    @Override // ClientAdapter
    public void clientConnectionFailed(Client client, Exception cause) {
        skClient.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        log.debug("Connection status set to: DISCONNECTED");
        skClient.setConnectionError(cause);
    }

    @Override // ClientAdapter
    public void clientDidLogoff(Client client) {
        skClient.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        log.debug("Connection status set to: DISCONNECTED");
    }

    @Override // ClientAdapter
    public void clientDidClear(Client client) {
        skClient.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        log.debug("Connection status set to: DISCONNECTED");
    }
}
