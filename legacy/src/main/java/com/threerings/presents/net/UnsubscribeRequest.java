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

package com.threerings.presents.net;

/**
 * Requests to end a subscription to a particular distributed object.
 */
public class UnsubscribeRequest extends UpstreamMessage
{
    /**
     * Zero argument constructor used when unserializing an instance.
     */
    public UnsubscribeRequest ()
    {
        super();
    }

    /**
     * Constructs a unsubscribe request for the distributed object
     * with the specified object id.
     */
    public UnsubscribeRequest (int oid)
    {
        _oid = oid;
    }

    /**
     * Returns the oid of the object from which we are unsubscribing.
     */
    public int getOid ()
    {
        return _oid;
    }

    @Override
    public String toString ()
    {
        return "[type=UNSUB, msgid=" + messageId + ", oid=" + _oid + "]";
    }

    /**
     * The object id of the distributed object from which we are
     * unsubscribing.
     */
    protected int _oid;
}
