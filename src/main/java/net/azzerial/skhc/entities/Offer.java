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

/**
 * Class Representing a buy or sell offer of the {@link net.azzerial.skhc.entities.Market}.
 *
 * @see ExchangeEvent ExchangeEvent
 * @see Market
 */
public class Offer {

    /** The volume of the current offer. */
    public final int volume;
    /** The price of the current offer. */
    public final int price;

    /* Constructors */

    public Offer(int volume, int price) {
        this.volume = volume;
        this.price = price;
    }

    /* Getters & Setters */

    /**
     * The {@link #volume} of this Offer object.
     *
     * @return The volume.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * The {@link #price} of this Offer object.
     *
     * @return The price.
     */
    public int getPrice() {
        return price;
    }

    /* Methods */

    @Override // Object
    public String toString() {
        return '{' +
            "volume=" + volume +
            ", price=" + price +
            '}';
    }
}
