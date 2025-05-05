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

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@Internal
public final class EventManager {

    private static final Logger log = LoggerFactory.getLogger("net.azzerial.skhc.events.EventManager");

    private final CopyOnWriteArraySet<net.azzerial.skhc.events.EventListener> listeners = new CopyOnWriteArraySet<>();

    /* Getters & Setters */

    @Internal
    @NotNull
    @UnmodifiableView
    public List<net.azzerial.skhc.events.EventListener> getRegisteredListeners() {
        return Collections.unmodifiableList(new ArrayList<>(listeners));
    }

    /* Methods */

    @Internal
    public void register(@NotNull Object listener) {
        Objects.requireNonNull(listener, "Provided listener cannot be null");
        if (!(listener instanceof net.azzerial.skhc.events.EventListener)) {
            throw new IllegalArgumentException("Provided listener does not extend EventListener");
        }
        listeners.add((net.azzerial.skhc.events.EventListener) listener);
    }

    @Internal
    public void unregister(@NotNull Object listener) {
        Objects.requireNonNull(listener, "Provided listener cannot be null");
        if (!(listener instanceof net.azzerial.skhc.events.EventListener)) {
            throw new IllegalArgumentException("Provided listener does not extend EventListener");
        }
        listeners.remove((net.azzerial.skhc.events.EventListener) listener);
    }

    @Internal
    public void notify(@NotNull GenericEvent event) {
        Objects.requireNonNull(event, "Provided event cannot be null");
        for (EventListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Throwable throwable) {
                log.error("An EventListener had an uncaught exception", throwable);
                if (throwable instanceof Error) {
                    throw (Error) throwable;
                }
            }
        }
    }
}
