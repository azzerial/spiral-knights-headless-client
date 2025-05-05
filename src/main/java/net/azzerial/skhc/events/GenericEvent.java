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

/**
 * Interface for events supported by the {@link SKClient}.
 *
 * @see EventListener#onEvent(GenericEvent)
 * @see ListenerAdapter#onGenericEvent(GenericEvent)
 */
public abstract class GenericEvent {

    protected final SKClient skClient;
    protected final Service service;

    /* Constructors */

    public GenericEvent(@NotNull SKClient skClient, @NotNull Service service) {
        this.skClient = skClient;
        this.service = service;
    }

    /* Getters & Setters */

    /**
     * The instance of the {@link SKClient} which received this event.
     *
     * @return The {@link SKClient} instance.
     *
     * @see SKClient
     */
    @NotNull
    public SKClient getSKClient() {
        return skClient;
    }

    /**
     * The {@link Service} which received this event.
     *
     * @return The {@link Service}.
     *
     * @see Service
     */
    @NotNull
    public Service getService() {
        return service;
    }
}
