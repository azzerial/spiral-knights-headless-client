//
// $Id$
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2012 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/narya/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.presents.dobj;

/**
 * Implemented by entities which wish to hear about changes that occur to set attributes of a
 * particular distributed object.
 *
 * <p> <b>NOTE:</b> This listener will receive <em>all</em> Entry events from a DObject it's
 * listening to, so it should check that the event's name matches the field it's interested in
 * before acting on the event.
 *
 * @see DObject#addListener
 *
 * @param <T> the type of entry being handled by this listener. This must match the type on the set
 * that generates the events.
 */
public interface SetListener<T extends DSet.Entry> extends ChangeListener
{
    /**
     * Called when an entry added event has been dispatched on an
     * object. This will be called <em>after</em> the event has been
     * applied to the object.
     *
     * @param event The event that was dispatched on the object.
     */
    void entryAdded (EntryAddedEvent<T> event);

    /**
     * Called when an entry updated event has been dispatched on an
     * object. This will be called <em>after</em> the event has been
     * applied to the object.
     *
     * @param event The event that was dispatched on the object.
     */
    void entryUpdated (EntryUpdatedEvent<T> event);

    /**
     * Called when an entry removed event has been dispatched on an
     * object. This will be called <em>after</em> the event has been
     * applied to the object.
     *
     * @param event The event that was dispatched on the object.
     */
    void entryRemoved (EntryRemovedEvent<T> event);
}
