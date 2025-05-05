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

import com.samskivert.util.StringUtil;
import com.threerings.presents.client.Client;
import com.threerings.presents.dobj.*;
import com.threerings.presents.util.SafeSubscriber;
import net.azzerial.skhc.events.EventManager;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Internal
public abstract class ServiceSubscriber<T extends DObject> implements Subscriber<T>, AttributeChangeListener {

    private static final Logger log = LoggerFactory.getLogger("net.azzerial.skhc.services.ServiceSubscriber");

    protected final Client client;
    protected final EventManager eventManager;
    protected final Service service;
    protected final SafeSubscriber<T> subscriber;

    /* Constructors */

    @Internal
    public ServiceSubscriber(@NotNull Client client, @NotNull EventManager eventManager, @NotNull Service service) {
        this.client = client;
        this.eventManager = eventManager;
        this.service = service;
        this.subscriber = new SafeSubscriber<>(service.getOid(client), this, this);
    }

    /* Methods */

    @Internal
    public final void subscribe() {
        subscriber.subscribe(client.getDObjectManager());
        log.debug("Subscribed to the {} service.", service.getCode());
    }

    @Internal
    public final void unsubscribe() {
        subscriber.unsubscribe(client.getDObjectManager());
        log.debug("Unsubscribed from the {} service.", service.getCode());
    }

    @Internal
    @Override // Subscriber<>
    public void objectAvailable(T object) {
        log.trace("[service={}, oid={}] => objectAvailable: {}", service.getCode(), object.getOid(), StringUtil.fieldsToString(object));
    }

    @Internal
    @Override // Subscriber<>
    public void requestFailed(int oid, ObjectAccessException cause) {
        log.trace("[service={}, oid={}] => requestFailed: {}", service.getCode(), oid, cause);
    }

    @Internal
    @Override // AttributeChangeListener
    public void attributeChanged(AttributeChangedEvent event) {
        log.trace("[service={}, oid={}] => attributeChanged: [{}={}]", service.getCode(), event.getTargetOid(), event.getName(), event.getValue());
    }
}
