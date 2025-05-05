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

package com.threerings.presents.server;

import com.google.common.collect.Maps;
import com.threerings.presents.client.TimeBaseService;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.data.TimeBaseCodes;
import com.threerings.presents.data.TimeBaseMarshaller;
import com.threerings.presents.data.TimeBaseObject;
import com.threerings.presents.dobj.RootDObjectManager;

import java.util.HashMap;

/**
 * Provides the server-side of the time base services. The time base services provide a means by
 * which delta times can be sent over the network which are expanded based on a shared base time
 * into full time stamps.
 */
public class TimeBaseProvider
    implements InvocationProvider, TimeBaseCodes
{
    /**
     * Registers the time provider with the appropriate managers. Called by the presents server at
     * startup.
     */
    public static void init (InvocationManager invmgr, RootDObjectManager omgr)
    {
        // we'll need these later
        _invmgr = invmgr;
        _omgr = omgr;

        // register a provider instance
        invmgr.registerProvider(new TimeBaseProvider(), TimeBaseMarshaller.class, GLOBAL_GROUP);
    }

    /**
     * Creates a time base object which can subsequently be fetched by the client and used to send
     * delta times.
     *
     * @param timeBase the name of the time base to create.
     *
     * @return the created and registered time base object.
     */
    public static TimeBaseObject createTimeBase (String timeBase)
    {
        TimeBaseObject object = _omgr.registerObject(new TimeBaseObject());
        _timeBases.put(timeBase, object);
        return object;
    }

    /**
     * Returns the named timebase object, or null if no time base object has been created with that
     * name.
     */
    public static TimeBaseObject getTimeBase (String timeBase)
    {
        return _timeBases.get(timeBase);
    }

    /**
     * Processes a request from a client to fetch the oid of the specified time object.
     */
    public void getTimeOid (ClientObject source, String timeBase,
                            TimeBaseService.GotTimeBaseListener listener)
        throws InvocationException
    {
        // look up the time base object in question
        TimeBaseObject time = getTimeBase(timeBase);
        if (time == null) {
            throw new InvocationException(NO_SUCH_TIME_BASE);
        }
        // and send the response
        listener.gotTimeOid(time.getOid());
    }

    /** Used to keep track of our time base objects. */
    protected static HashMap<String,TimeBaseObject> _timeBases = Maps.newHashMap();

    /** The invocation manager with which we interoperate. */
    protected static InvocationManager _invmgr;

    /** The distributed object manager with which we interoperate. */
    protected static RootDObjectManager _omgr;
}
