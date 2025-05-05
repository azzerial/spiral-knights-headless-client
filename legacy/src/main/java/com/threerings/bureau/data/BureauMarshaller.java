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

package com.threerings.bureau.data;

import com.threerings.bureau.client.BureauService;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.data.InvocationMarshaller;

import javax.annotation.Generated;

/**
 * Provides the implementation of the {@link BureauService} interface
 * that marshalls the arguments and delivers the request to the provider
 * on the server. Also provides an implementation of the response listener
 * interfaces that marshall the response arguments and deliver them back
 * to the requesting client.
 */
@Generated(value={"com.threerings.presents.tools.GenServiceTask"},
           comments="Derived from BureauService.java.")
public class BureauMarshaller extends InvocationMarshaller<ClientObject>
    implements BureauService
{
    /** The method id used to dispatch {@link #agentCreated} requests. */
    public static final int AGENT_CREATED = 1;

    // from interface BureauService
    public void agentCreated (int arg1)
    {
        sendRequest(AGENT_CREATED, new Object[] {
            Integer.valueOf(arg1)
        });
    }

    /** The method id used to dispatch {@link #agentCreationFailed} requests. */
    public static final int AGENT_CREATION_FAILED = 2;

    // from interface BureauService
    public void agentCreationFailed (int arg1)
    {
        sendRequest(AGENT_CREATION_FAILED, new Object[] {
            Integer.valueOf(arg1)
        });
    }

    /** The method id used to dispatch {@link #agentDestroyed} requests. */
    public static final int AGENT_DESTROYED = 3;

    // from interface BureauService
    public void agentDestroyed (int arg1)
    {
        sendRequest(AGENT_DESTROYED, new Object[] {
            Integer.valueOf(arg1)
        });
    }

    /** The method id used to dispatch {@link #bureauError} requests. */
    public static final int BUREAU_ERROR = 4;

    // from interface BureauService
    public void bureauError (String arg1)
    {
        sendRequest(BUREAU_ERROR, new Object[] {
            arg1
        });
    }

    /** The method id used to dispatch {@link #bureauInitialized} requests. */
    public static final int BUREAU_INITIALIZED = 5;

    // from interface BureauService
    public void bureauInitialized (String arg1)
    {
        sendRequest(BUREAU_INITIALIZED, new Object[] {
            arg1
        });
    }
}
