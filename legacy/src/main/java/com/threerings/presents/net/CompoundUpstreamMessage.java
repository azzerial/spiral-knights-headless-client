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

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Groups messages to be dispatched without triggering the message throttle. Any messages sent to
 * a client while processing an upstream message will be sent back en masse in a
 * CompoundDownstreamMessage.
 */
public class CompoundUpstreamMessage extends UpstreamMessage
{
    public List<UpstreamMessage> msgs = Lists.newArrayList();

    @Override
    public String toString ()
    {
        return "[type=COMPOUND, msgid=" + messageId + "]";
    }
}
