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

package net.azzerial.skhc.entities;

import net.azzerial.skhc.events.exchange.ExchangeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * The exchange market values as observable on the <a href="https://wiki.spiralknights.com/Energy_Depot#Market">energy depot market tab</a>.
 *
 * @see ExchangeEvent ExchangeEvent
 * @see Offer
 */
public class Market {

    /** The latest trade price at which 100 energy were sold. */
    public final int lastPrice;
    /**
     * An array with the 5 best {@link Offer offers} buying 100 energy for crowns.
     *
     * @see Offer
     */
    public final Offer[] buyOffers;
    /**
     * An array with the 5 best {@link Offer offers} selling 100 energy for crowns.
     *
     * @see Offer
     */
    public final Offer[] sellOffers;

    /* Constructors */

    public Market(int lastPrice, Offer[] buyOffers, Offer[] sellOffers) {
        this.lastPrice = lastPrice;
        this.buyOffers = buyOffers;
        this.sellOffers = sellOffers;
    }

    /* Getters & Setters */

    /**
     * The {@link #lastPrice last trade price} of this Market object.
     *
     * @return The last trade price.
     */
    public int getLastPrice() {
        return lastPrice;
    }

    /**
     * The {@link #buyOffers top 5 best buy offers} of this Market object.
     *
     * @return The top 5 best buy offers.
     *
     * @see Offer
     */
    @NotNull
    public Offer[] getBuyOffers() {
        return buyOffers;
    }

    /**
     * The {@link #sellOffers top 5 best sell offers} of this Market object.
     *
     * @return The top 5 best sell offers.
     *
     * @see Offer
     */
    @NotNull
    public Offer[] getSellOffers() {
        return sellOffers;
    }

    /* Methods */

    @Override // Object
    public String toString() {
        return '{' +
            "lastPrice=" + lastPrice +
            ", buyOffers=" + Arrays.toString(buyOffers) +
            ", sellOffers=" + Arrays.toString(sellOffers) +
            '}';
    }
}
