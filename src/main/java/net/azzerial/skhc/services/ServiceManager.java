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

import net.azzerial.skhc.SKClientImpl;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Internal
public final class ServiceManager {

    private static final Logger log = LoggerFactory.getLogger("net.azzerial.skhc.services.ServiceManager");

    private final CopyOnWriteArraySet<ServiceSubscriber<?>> subscribers;

    /* Constructors */

    @Internal
    public ServiceManager(@NotNull SKClientImpl skClient, @NotNull EnumSet<Service> services) {
        this.subscribers =
            services.stream()
                .map((it) -> it.getSubscriber(skClient))
                .collect(Collectors.toCollection(CopyOnWriteArraySet::new));
    }

    /* Methods */

    @Internal
    public void subscribe() {
        log.debug("Subscribing to the services...");
        subscribers.forEach(ServiceSubscriber::subscribe);
    }

    @Internal
    public void unsubscribe() {
        log.debug("Unsubscribing from the services...");
        subscribers.forEach(ServiceSubscriber::unsubscribe);
    }
}
