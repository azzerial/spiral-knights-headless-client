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
 * Implemented by entities which wish to hear about element updates that take place for a
 * particular distributed object.
 *
 * @see DObject#addListener
 */
public interface ElementUpdateListener extends ChangeListener
{
    /**
     * Called when an element updated event has been dispatched on an object. This will be called
     * <em>after</em> the event has been applied to the object. So fetching the element during
     * this call will provide the new value for the element.
     *
     * @param event The event that was dispatched on the object.
     */
    void elementUpdated (ElementUpdatedEvent event);
}
