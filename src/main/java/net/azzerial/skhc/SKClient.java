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

import net.azzerial.skhc.enums.ConnectionStatus;
import net.azzerial.skhc.enums.Language;
import net.azzerial.skhc.enums.Region;
import net.azzerial.skhc.events.EventListener;
import net.azzerial.skhc.events.ListenerAdapter;
import net.azzerial.skhc.services.Service;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.LoginException;
import java.util.Collection;

/**
 * The core of Spiral Knights Headless Client used to register {@link EventListener event listeners} and handle session state.
 *
 * <p> The configuration of the SKClient is made in two phases:
 * <ol>
 *     <li>Through the {@link SKClientBuilder}, specify which account will be used for the session and configure the game {@link Language language}, server {@link Region region} and {@link Service services} which will be active.</li>
 *     <li>Through the SKClient, register {@link EventListener event listeners} - which will receive updates from the enabled game {@link Service services} - and handle client session state - through {@link SKClient#connect()} and {@link SKClient#disconnect()} -.</li>
 * </ol>
 *
 * <p><b>Example</b><br>
 * <pre><code>
 * try {
 *     final SKClient client = ...;
 *
 *     client.addEventListeners(new ListenerAdapter() {
 *         {@literal @Override}
 *         public void onGenericEvent(@NotNull GenericEvent event) {
 *             System.out.println("event received");
 *         }
 *     });
 *
 *     client.connect();
 * } catch (LoginException e) {
 *     e.printStackTrace();
 * }
 * </code></pre>
 *
 * @see    SKClientBuilder
 * @see    #addEventListeners(Object, Object...)
 * @see    #addEventListeners(Collection)
 * @see    #connect()
 * @see    #disconnect()
 * */
public interface SKClient {

    /* Getters & Setters */

    /**
     * Get the current {@link ConnectionStatus} of the SKClient instance.
     *
     * @return The current {@link ConnectionStatus}.
     *
     * @see    ConnectionStatus
     * @see    #isConnectionStatus(ConnectionStatus)
     */
    @NotNull
    ConnectionStatus getConnectionStatus();

    /**
     * Used to determine whether the current {@link ConnectionStatus} of the SKClient instance is of a specific value.
     *
     * @param connectionStatus
     *        The {@link ConnectionStatus} to compare to the current one.
     *
     * @return {@code true} if the current {@link ConnectionStatus} of the SKClient instance is of a provided value.
     *
     * @see    ConnectionStatus
     * @see    #getConnectionStatus()
     */
    boolean isConnectionStatus(@Nullable ConnectionStatus connectionStatus);

    /* Methods */

    /**
     * Connect to the Spiral Knights server and start a session.
     *
     * @return {@code true} if an attempt to connect has been initiated, otherwise {@code false} if the client is already connected.
     *
     * @throws LoginException
     *         If the provided Spiral Knights account credentials were incorrect or the client could not open a connection with the server.
     *
     * @see    #disconnect()
     */
    @CheckReturnValue
    boolean connect() throws LoginException;

    /**
     * Disconnect from the Spiral Knights server and terminate the session.
     *
     * @return {@code true} if the disconnection succeeded, otherwise {@code false} if it failed.
     *
     * @see    #connect()
     */
    @CheckReturnValue
    boolean disconnect();

    /**
     * Add all provided {@link EventListener listeners} to the event manager that will be handling events.
     *
     * @param listener
     *        A {@link EventListener listener} which will receive events.
     *
     * @param listeners
     *        Other {@link EventListener listeners} which will receive events.
     *
     * @return The SKClient instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    EventListener EventListener
     * @see    ListenerAdapter ListenerAdapter
     * @see    #addEventListeners(Collection)
     * @see    #removeEventListeners(Object, Object...)
     */
    @NotNull
    SKClient addEventListeners(@NotNull Object listener, @NotNull Object... listeners);

    /**
     * Add all provided {@link EventListener listeners} to the event manager that will be handling events.
     *
     * @param listeners
     *        The {@link EventListener listeners} which will receive events.
     *
     * @return The SKClient instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    EventListener EventListener
     * @see    ListenerAdapter ListenerAdapter
     * @see    #addEventListeners(Object, Object...)
     * @see    #removeEventListeners(Collection)
     */
    @NotNull
    SKClient addEventListeners(@NotNull Collection<Object> listeners);

    /**
     * Remove all provided {@link EventListener listeners} from the event manager that will be handling events.
     *
     * @param listener
     *        A {@link EventListener listener} which will cease to receive events.
     *
     * @param listeners
     *        Other {@link EventListener listeners} which will cease to receive events.
     *
     * @return The SKClient instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    EventListener EventListener
     * @see    ListenerAdapter ListenerAdapter
     * @see    #removeEventListeners(Collection)
     * @see    #addEventListeners(Object, Object...)
     */
    @NotNull
    SKClient removeEventListeners(@NotNull Object listener, @NotNull Object... listeners);

    /**
     * Remove all provided {@link EventListener listeners} from the event manager that will be handling events.
     *
     * @param listeners
     *        The {@link EventListener listeners} which will cease to receive events.
     *
     * @return The SKClient instance, to be used for chaining.
     *
     * @throws NullPointerException
     *         If provided with {@code null}.
     *
     * @see    EventListener EventListener
     * @see    ListenerAdapter ListenerAdapter
     * @see    #removeEventListeners(Object, Object...)
     * @see    #addEventListeners(Collection)
     */
    @NotNull
    SKClient removeEventListeners(@NotNull Collection<Object> listeners);
}
