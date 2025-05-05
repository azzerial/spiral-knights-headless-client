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

package com.threerings.crowd.peer.server;

import com.threerings.crowd.chat.client.ChatService;
import com.threerings.crowd.chat.data.UserMessage;
import com.threerings.crowd.peer.client.CrowdPeerService;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.server.InvocationException;
import com.threerings.presents.server.InvocationProvider;
import com.threerings.util.Name;

import javax.annotation.Generated;

/**
 * Defines the server-side of the {@link CrowdPeerService}.
 */
@Generated(value={"com.threerings.presents.tools.GenServiceTask"},
           comments="Derived from CrowdPeerService.java.")
public interface CrowdPeerProvider extends InvocationProvider
{
    /**
     * Handles a {@link CrowdPeerService#deliverBroadcast} request.
     */
    void deliverBroadcast (ClientObject caller, Name arg1, byte arg2, String arg3, String arg4);

    /**
     * Handles a {@link CrowdPeerService#deliverTell} request.
     */
    void deliverTell (ClientObject caller, UserMessage arg1, Name arg2, ChatService.TellListener arg3)
        throws InvocationException;
}
