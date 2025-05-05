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

import net.azzerial.skhc.SKClient;
import net.azzerial.skhc.services.Service;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Interface through which {@link GenericEvent events} received by the {@link Service services} are distributed.
 * <br>Register which EventListeners will be receiving events by using either {@link SKClient#addEventListeners(Object, Object...)} or {@link SKClient#addEventListeners(Collection)} methods.
 *
 * <p>For convenience, you can extend the {@link net.azzerial.skhc.events.ListenerAdapter} and benefit of per event handlers.
 *
 * @see ListenerAdapter
 * @see SKClient#addEventListeners(Object, Object...)
 * @see SKClient#addEventListeners(Collection)
 * @see SKClient#removeEventListeners(Object, Object...)
 * @see SKClient#removeEventListeners(Collection)
 */
public interface EventListener {

    /**
     * Called for every single event regardless of which Service received it.
     */
    void onEvent(@NotNull GenericEvent event);
}
