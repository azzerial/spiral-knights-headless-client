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

package net.azzerial.skhc.services;

import com.threerings.presents.client.Client;
import com.threerings.projectx.data.ProjectXBootstrapData;
import net.azzerial.skhc.SKClient;
import net.azzerial.skhc.SKClientBuilder;
import net.azzerial.skhc.SKClientImpl;
import net.azzerial.skhc.events.ListenerAdapter;
import net.azzerial.skhc.events.exchange.ExchangeEvent;
import net.azzerial.skhc.events.exchange.ExchangeUpdateEvent;
import net.azzerial.skhc.services.exchange.ExchangeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

/**
 * Flags which enable or disable specific services - and thus events - of the {@link SKClient}.
 *
 * <ul>
 *     <li><b>EXCHANGE</b> - all events related to the <a href="https://wiki.spiralknights.com/Energy_Depot#Market">energy depot market tab</a>.</li>
 * </ul>
 *
 * @see SKClientBuilder#enableServices(net.azzerial.skhc.services.Service, net.azzerial.skhc.services.Service...) SKClientBuilder.enableServices(Service, Service...)
 * @see SKClientBuilder#enableServices(Collection) SKClientBuilder.enableServices(Collection)
 * @see SKClientBuilder#enableAllServices() SKClientBuilder.enableAllServices()
 * @see SKClientBuilder#disableServices(net.azzerial.skhc.services.Service, net.azzerial.skhc.services.Service...) SKClientBuilder.disableServices(Service, Service...)
 * @see SKClientBuilder#disableServices(Collection) SKClientBuilder.disableServices(Collection)
 * @see SKClientBuilder#disableAllServices() SKClientBuilder.disableAllServices()
 */
public enum Service {
    /**
     * The service receiving events related to <a href="https://wiki.spiralknights.com/Energy_Depot#Market">energy depot market tab</a>.
     *
     * <p><b>Events</b>:
     * <ul>
     *     <li>{@link ExchangeEvent ExchangeEvent} - sent when the session opens.</li>
     *     <li>{@link ExchangeUpdateEvent ExchangeUpdateEvent} - sent whenever the market values change.</li>
     * </ul>
     *
     * @see ExchangeEvent ExchangeEvent
     * @see ExchangeUpdateEvent ExchangeUpdateEvent
     * @see ListenerAdapter#onExchange(ExchangeEvent) ListenerAdapter.onExchange(ExchangeEvent)
     * @see ListenerAdapter#onExchangeUpdate(ExchangeUpdateEvent) ListenerAdapter.onExchangeUpdate(ExchangeUpdateEvent)
     */
    EXCHANGE(
        "exchange",
        (client -> ((ProjectXBootstrapData) client.getBootstrapData()).exchangeOid),
        ExchangeService::new
    );

    private final String code;
    private final Function<Client, Integer> oid;
    private final Function<SKClientImpl, ServiceSubscriber<?>> constructor;

    /* Constructors */

    Service(@NotNull String code, @NotNull Function<Client, Integer> oid, Function<SKClientImpl, @NotNull ServiceSubscriber<?>> constructor) {
        this.code = code;
        this.oid = oid;
        this.constructor = constructor;
    }

    /* Getters & Setters */

    /**
     * The internal code used to represent the service.
     *
     * @return The internal code of this service.
     */
    @NotNull
    public String getCode() {
        return code;
    }

    int getOid(@NotNull Client client) {
        return oid.apply(client);
    }

    @NotNull
    ServiceSubscriber<?> getSubscriber(@NotNull SKClient skClient) {
        return constructor.apply((SKClientImpl) skClient);
    }

    /* Methods */

    /**
     * Retrieve the Service based on the provided code.
     *
     * @param  code
     *         The code relating to the Service we wish to retrieve.
     *
     * @return The Service matching the code, otherwise if there is no match returns {@code null}.
     */
    @Nullable
    public static Service fromCode(@Nullable String code) {
        for (Service service : values()) {
            if (service.code.equalsIgnoreCase(code)) {
                return service;
            }
        }
        return null;
    }
}
