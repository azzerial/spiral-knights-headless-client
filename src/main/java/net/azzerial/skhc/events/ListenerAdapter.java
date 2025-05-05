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

package net.azzerial.skhc.events;

import net.azzerial.skhc.entities.Market;
import net.azzerial.skhc.events.exchange.ExchangeEvent;
import net.azzerial.skhc.events.exchange.ExchangeUpdateEvent;
import net.azzerial.skhc.services.Service;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Abstract implementation of {@link EventListener} which divides {@link GenericEvent GenericEvents} for you.
 * You should <b><u>override</u></b> the methods provided by this class for your event listener implementation.
 *
 * <p><b>Example</b><br>
 * <pre><code>
 * public class MyExchangeListener extends ListenerAdapter {
 *
 *     {@literal @Override}
 *     public void onExchange(@NotNull ExchangeEvent event) {
 *         System.out.printf("Session started with market price at: %d\n", event.market.getLastPrice());
 *     }
 *
 *     {@literal @Override}
 *     public void onExchangeUpdate(@NotNull ExchangeUpdateEvent event) {
 *         System.out.printf("Market price move to: %d\n", event.market.getLastPrice());
 *     }
 * }
 * </code></pre>
 *
 * @see EventListener
 */
public abstract class ListenerAdapter implements EventListener {

    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final ConcurrentMap<Class<?>, MethodHandle> methods = new ConcurrentHashMap<>();

    /* Methods */

    /**
     * Called for every single event regardless of which {@link Service Service} received it.
     *
     * @see Service
     * @see GenericEvent
     */
    public void onGenericEvent(@NotNull GenericEvent event) {}

    /**
     * Called when the session opens and receives the {@link Market Market} object for the first time.
     *
     * @see Service#EXCHANGE
     * @see net.azzerial.skhc.events.exchange.ExchangeEvent
     */
    public void onExchange(@NotNull ExchangeEvent event) {}

    /**
     * Called whenever the {@link Market Market} values changed.
     *
     * @see Service#EXCHANGE
     * @see net.azzerial.skhc.events.exchange.ExchangeUpdateEvent
     */
    public void onExchangeUpdate(@NotNull ExchangeUpdateEvent event) {}

    @Internal
    @Override // EventListener
    public final void onEvent(@NotNull GenericEvent event) {
        onGenericEvent(event);

        final Class<?> eventClass = event.getClass();
        final MethodHandle handle = methods.computeIfAbsent(eventClass, (it) -> {
            final String name = it.getSimpleName();

            try {
                return lookup.findVirtual(
                    ListenerAdapter.class,
                    "on" + name.substring(0, name.length() - "Event".length()),
                    MethodType.methodType(Void.TYPE, it)
                );
            } catch (NoSuchMethodException | IllegalAccessException ignored) {}
            return null;
        });

        if (handle == null) {
            return;
        }
        try {
            handle.invoke(this, event);
        } catch (Throwable cause) {
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new IllegalStateException(cause);
        }
    }
}
