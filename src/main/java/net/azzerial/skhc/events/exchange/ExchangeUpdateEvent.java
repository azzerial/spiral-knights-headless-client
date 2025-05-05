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

import com.samskivert.util.StringUtil;
import net.azzerial.skhc.SKClient;
import net.azzerial.skhc.entities.Market;
import net.azzerial.skhc.entities.Offer;
import net.azzerial.skhc.events.ListenerAdapter;
import net.azzerial.skhc.services.Service;
import net.azzerial.skhc.services.exchange.ExchangeService;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * An event received by the {@link Service#EXCHANGE} whenever the {@link Market} object changes.
 *
 * @see Service#EXCHANGE
 * @see Market
 * @see ListenerAdapter#onExchangeUpdate(ExchangeUpdateEvent)  ListenerAdapter.onExchangeUpdate(ExchangeUpdateEvent)
 */
public class ExchangeUpdateEvent extends ExchangeEvent {

    /** The attribute name of {@link Market#lastPrice}. */
    public static final String LAST_PRICE = "lastPrice";
    /** The attribute name of {@link Market#buyOffers}. */
    public static final String BUY_OFFERS = "buyOffers";
    /** The attribute name of {@link Market#sellOffers}. */
    public static final String SELL_OFFERS = "sellOffers";

    private final ExchangeService.AttributeChange attribute;

    /* Constructors */

    @Internal
    public ExchangeUpdateEvent(@NotNull SKClient skClient, @NotNull Market market, @NotNull ExchangeService.AttributeChange attribute) {
        super(skClient, market);
        this.attribute = attribute;
    }

    /* Getters & Setters */

    /**
     * The attribute name of the {@link Market} object which got updated.
     *
     * @return The name of the updated attribute.
     *
     * @see #LAST_PRICE
     * @see #BUY_OFFERS
     * @see #SELL_OFFERS
     */
    @NotNull
    public String getAttributeName() {
        return attribute.name;
    }

    /**
     * The old value of the updated attribute.
     *
     * @return The old value.
     */
    @NotNull
    public Object getOldValue() {
        return attribute.oldValue;
    }

    /**
     * The new value of the updated attribute.
     *
     * @return The new value.
     */
    @NotNull
    public Object getNewValue() {
        return attribute.newValue;
    }

    /* Methods */

    /**
     * A convenient way to handle an ExchangeUpdateEvent.
     * The method takes care of identifying the attribute which value changed and call the appropriate {@link ExchangeUpdateHandler handler} method.
     *
     * @param handler
     *        The {@link ExchangeUpdateHandler handler} which methods will be called.
     *
     * @see ExchangeUpdateHandler
     * @see ExchangeUpdateHandler#onLastPriceChange(int, int) ExchangeUpdateHandler.onLastPriceChange(int, int)
     * @see ExchangeUpdateHandler#onBuyOffersChange(Offer[], Offer[]) ExchangeUpdateHandler.onBuyOffersChange(Offer[], Offer[])
     * @see ExchangeUpdateHandler#onSellOffersChange(Offer[], Offer[]) ExchangeUpdateHandler.onSellOffersChange(Offer[], Offer[])
     */
    public void handle(@NotNull ExchangeUpdateHandler handler) {
        switch (attribute.name) {
            case LAST_PRICE:
                handler.onLastPriceChange(
                    (int) attribute.newValue,
                    (int) attribute.oldValue
                );
                break;
            case BUY_OFFERS:
                handler.onBuyOffersChange(
                    (Offer[]) attribute.newValue,
                    (Offer[]) attribute.oldValue
                );
                break;
            case SELL_OFFERS:
                handler.onSellOffersChange(
                    (Offer[]) attribute.newValue,
                    (Offer[]) attribute.oldValue
                );
                break;
        }
    }

    @Override // Object
    public String toString() {
        return '{' +
            "name=" + attribute.name +
            ", value=" + StringUtil.toString(attribute.newValue) +
            '}';
    }

    /* Inner Classes */

    /**
     * Interface made to simplify the handling of ExchangeUpdateEvents.
     * 
     * @see #handle(ExchangeUpdateHandler) handle(ExchangeUpdateHandler)
     */
    public interface ExchangeUpdateHandler {

        // TODO: no @param for newLastPrice
        // TODO: no @param for oldLastPrice
        /**
         * Method called on {@link Market#lastPrice} value changes.
         *
         * @see #handle(ExchangeUpdateHandler) handle(ExchangeUpdateHandler)
         */
        default void onLastPriceChange(int newLastPrice, int oldLastPrice) {}

        // TODO: no @param for newBuyOffers
        // TODO: no @param for oldBuyOffers
        /**
         * Method called on {@link Market#buyOffers} value changes.
         *
         * @see #handle(ExchangeUpdateHandler) handle(ExchangeUpdateHandler)
         */
        default void onBuyOffersChange(@NotNull Offer[] newBuyOffers, @NotNull Offer[] oldBuyOffers) {}

        // TODO: no @param for newSellOffers
        // TODO: no @param for oldSellOffers
        /**
         * Method called on {@link Market#sellOffers} value changes.
         *
         * @see #handle(ExchangeUpdateHandler) handle(ExchangeUpdateHandler)
         */
        default void onSellOffersChange(@NotNull Offer[] newSellOffers, @NotNull Offer[] oldSellOffers) {}
    }
}
