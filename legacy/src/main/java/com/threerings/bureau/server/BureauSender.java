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

package com.threerings.bureau.server;

import com.threerings.bureau.client.BureauDecoder;
import com.threerings.bureau.client.BureauReceiver;
import com.threerings.presents.data.ClientObject;
import com.threerings.presents.server.InvocationSender;

/**
 * Used to issue notifications to a {@link BureauReceiver} instance on a
 * client.
 */
public class BureauSender extends InvocationSender
{
    /**
     * Issues a notification that will result in a call to {@link
     * BureauReceiver#createAgent} on a client.
     */
    public static void createAgent (
        ClientObject target, int arg1)
    {
        sendNotification(
            target, BureauDecoder.RECEIVER_CODE, BureauDecoder.CREATE_AGENT,
            new Object[] { Integer.valueOf(arg1) });
    }

    /**
     * Issues a notification that will result in a call to {@link
     * BureauReceiver#destroyAgent} on a client.
     */
    public static void destroyAgent (
        ClientObject target, int arg1)
    {
        sendNotification(
            target, BureauDecoder.RECEIVER_CODE, BureauDecoder.DESTROY_AGENT,
            new Object[] { Integer.valueOf(arg1) });
    }

}
