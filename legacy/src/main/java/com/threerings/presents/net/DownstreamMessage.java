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
 * This class encapsulates a message in the distributed object protocol that flows from the server
 * to the client. Downstream messages include object subscription, event forwarding and session
 * management.
 */
public abstract class DownstreamMessage extends Message
{
    /**
     * The message id of the upstream message with which this downstream message is associated (or
     * -1 if it is not associated with any upstream message).
     */
    public short messageId = -1;

    @Override
    public String toString ()
    {
        return "[msgid=" + messageId + "]";
    }
}
