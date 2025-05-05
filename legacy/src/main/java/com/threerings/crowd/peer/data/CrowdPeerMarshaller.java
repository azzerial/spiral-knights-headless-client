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

package com.threerings.crowd.peer.data;

import com.threerings.crowd.chat.client.ChatService;
import com.threerings.crowd.chat.data.ChatMarshaller;
import com.threerings.crowd.chat.data.UserMessage;
import com.threerings.crowd.peer.client.CrowdPeerService;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.data.InvocationMarshaller;
import com.threerings.util.Name;

import javax.annotation.Generated;

/**
 * Provides the implementation of the {@link CrowdPeerService} interface
 * that marshalls the arguments and delivers the request to the provider
 * on the server. Also provides an implementation of the response listener
 * interfaces that marshall the response arguments and deliver them back
 * to the requesting client.
 */
@Generated(value={"com.threerings.presents.tools.GenServiceTask"},
           comments="Derived from CrowdPeerService.java.")
public class CrowdPeerMarshaller extends InvocationMarshaller<ClientObject>
    implements CrowdPeerService
{
    /** The method id used to dispatch {@link #deliverBroadcast} requests. */
    public static final int DELIVER_BROADCAST = 1;

    // from interface CrowdPeerService
    public void deliverBroadcast (Name arg1, byte arg2, String arg3, String arg4)
    {
        sendRequest(DELIVER_BROADCAST, new Object[] {
            arg1, Byte.valueOf(arg2), arg3, arg4
        });
    }

    /** The method id used to dispatch {@link #deliverTell} requests. */
    public static final int DELIVER_TELL = 2;

    // from interface CrowdPeerService
    public void deliverTell (UserMessage arg1, Name arg2, ChatService.TellListener arg3)
    {
        ChatMarshaller.TellMarshaller listener3 = new ChatMarshaller.TellMarshaller();
        listener3.listener = arg3;
        sendRequest(DELIVER_TELL, new Object[] {
            arg1, arg2, listener3
        });
    }
}
