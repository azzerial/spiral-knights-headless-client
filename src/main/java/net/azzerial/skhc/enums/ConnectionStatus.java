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

package net.azzerial.skhc.enums;

import net.azzerial.skhc.SKClient;

/**
 * Enum representing the connection status of a session of the {@link SKClient SKClient}.
 */
public enum ConnectionStatus {
    /**
     * Indicates that the process of connection of the session has been initiated.
     */
    PENDING(true),
    /**
     * Indicates that the session is currently connected.
     */
    CONNECTED(true),
    /**
     * Indicates that the session is currently disconnected.
     */
    DISCONNECTED;

    private final boolean connectionCycle;

    /* Constructors */

    ConnectionStatus() {
        this(false);
    }

    ConnectionStatus(boolean connectionCycle) {
        this.connectionCycle = connectionCycle;
    }

    /* Getters & Setters */

    /**
     * Whether this ConnectionStatus is a part of the connection cycle.
     * <br>The connection cycle includes both the {@link #PENDING} and the {@link #CONNECTED} states.
     *
     * @return {@code true} if this ConnectionStatus is part of the connection cycle, otherwise {@code false}.
     */
    public boolean isConnectionCycle() {
        return connectionCycle;
    }

    /**
     * Whether this ConnectionStatus is {@link #PENDING}.
     *
     * @return {@code true} if this ConnectionStatus is {@link #PENDING}, otherwise {@code false}.
     */
    public boolean isPending() {
        return this == ConnectionStatus.PENDING;
    }

    /**
     * Whether this ConnectionStatus is {@link #CONNECTED}.
     *
     * @return {@code true} if this ConnectionStatus is {@link #CONNECTED}, otherwise {@code false}.
     */
    public boolean isConnected() {
        return this == ConnectionStatus.CONNECTED;
    }

    /**
     * Whether this ConnectionStatus is {@link #DISCONNECTED}.
     *
     * @return {@code true} if this ConnectionStatus is {@link #DISCONNECTED}, otherwise {@code false}.
     */
    public boolean isDisconnected() {
        return this == ConnectionStatus.DISCONNECTED;
    }
}