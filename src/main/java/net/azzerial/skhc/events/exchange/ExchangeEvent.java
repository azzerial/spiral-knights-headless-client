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

package net.azzerial.skhc.events.exchange;

import net.azzerial.skhc.SKClient;
import net.azzerial.skhc.entities.Market;
import net.azzerial.skhc.events.GenericEvent;
import net.azzerial.skhc.events.ListenerAdapter;
import net.azzerial.skhc.services.Service;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * An event received by the {@link Service#EXCHANGE} containing a copy of the current {@link Market} object.
 *
 * @see Service#EXCHANGE
 * @see Market
 * @see ListenerAdapter#onExchange(ExchangeEvent) ListenerAdapter.onExchange(ExchangeEvent)
 */
public class ExchangeEvent extends GenericEvent {

    public final Market market;

    /* Constructors */

    @Internal
    public ExchangeEvent(@NotNull SKClient skClient, @NotNull Market market) {
        super(skClient, Service.EXCHANGE);
        this.market = market;
    }

    /* Getters & Setters */

    /**
     * A copy of the current state of the {@link Market} object.
     *
     * @return The {@link Market} object.
     *
     * @see Market
     */
    @NotNull
    public Market getMarket() {
        return market;
    }
}
