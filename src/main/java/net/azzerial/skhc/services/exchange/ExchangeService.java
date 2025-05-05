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

package net.azzerial.skhc.services.exchange;

import com.threerings.presents.dobj.AttributeChangedEvent;
import com.threerings.projectx.exchange.data.ConsolidatedOffer;
import com.threerings.projectx.exchange.data.ExchangeObject;
import net.azzerial.skhc.SKClient;
import net.azzerial.skhc.SKClientImpl;
import net.azzerial.skhc.entities.Market;
import net.azzerial.skhc.entities.Offer;
import net.azzerial.skhc.events.exchange.ExchangeEvent;
import net.azzerial.skhc.events.exchange.ExchangeUpdateEvent;
import net.azzerial.skhc.services.Service;
import net.azzerial.skhc.services.ServiceSubscriber;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import static net.azzerial.skhc.events.exchange.ExchangeUpdateEvent.*;

@Internal
public final class ExchangeService extends ServiceSubscriber<ExchangeObject> {

    private static final Service SERVICE = Service.EXCHANGE;

    private final SKClient skClient;
    private final Cache cache = new Cache();

    /* Constructors */

    @Internal
    public ExchangeService(@NotNull SKClientImpl skClient) {
        super(Objects.requireNonNull(skClient.getClient()), skClient.getEventManager(), SERVICE);
        this.skClient = skClient;
    }

    /* Methods */

    @Internal
    @Override // PresentSubscriber<>
    public void objectAvailable(ExchangeObject object) {
        super.objectAvailable(object);
        this.cache.lastPrice = object.lastPrice;
        this.cache.buyOffers = mapOffers(object.buyOffers);
        this.cache.sellOffers = mapOffers(object.sellOffers);
        eventManager.notify(new ExchangeEvent(skClient, cache.toMarketObject()));
    }

    @Internal
    @Override // PresentSubscriber<>
    public void attributeChanged(AttributeChangedEvent event) {
        super.attributeChanged(event);
        final AttributeChange attribute = new AttributeChange(event.getName());
        switch (event.getName()) {
            case LAST_PRICE:
                this.cache.lastPrice = (int) event.getValue();
                attribute.oldValue = event.getOldValue();
                attribute.newValue = event.getValue();
                break;
            case BUY_OFFERS:
                this.cache.buyOffers = mapOffers((ConsolidatedOffer[]) event.getValue());
                attribute.oldValue = mapOffers((ConsolidatedOffer[]) event.getOldValue());
                attribute.newValue = mapOffers((ConsolidatedOffer[]) event.getValue());
                break;
            case SELL_OFFERS:
                this.cache.sellOffers = mapOffers((ConsolidatedOffer[]) event.getValue());
                attribute.oldValue = mapOffers((ConsolidatedOffer[]) event.getOldValue());
                attribute.newValue = mapOffers((ConsolidatedOffer[]) event.getValue());
                break;
        }
        eventManager.notify(new ExchangeUpdateEvent(skClient, cache.toMarketObject(), attribute));
    }

    /* Internal */

    @NotNull
    private static Offer[] mapOffers(@NotNull ConsolidatedOffer[] offers) {
        return Arrays.stream(offers)
            .map((it) -> new Offer(it.volume, it.price))
            .toArray(Offer[]::new);
    }

    /* Inner Classes */

    public static final class AttributeChange {

        public final String name;
        public Object oldValue;
        public Object newValue;

        /* Methods */

        public AttributeChange(String name) {
            this.name = name;
        }
    }

    private static final class Cache {

        private int lastPrice;
        private Offer[] buyOffers;
        private Offer[] sellOffers;

        /* Methods */

        @NotNull
        private Market toMarketObject() {
            return new Market(
                lastPrice,
                deepCopyOffers(buyOffers),
                deepCopyOffers(sellOffers)
            );
        }

        /* Internal */

        @NotNull
        private static Offer[] deepCopyOffers(@NotNull Offer[] offers) {
            return Arrays.stream(offers)
                .map((it) -> new Offer(it.volume, it.price))
                .toArray(Offer[]::new);
        }
    }
}
