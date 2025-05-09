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

package com.threerings.crowd.chat.data;

import com.threerings.io.SimpleStreamableObject;
import com.threerings.presents.dobj.DSet;

/**
 * Represents a chat channel.
 */
public abstract class ChatChannel extends SimpleStreamableObject
    implements Comparable<ChatChannel>, DSet.Entry
{
    // from interface Comparable<ChatChannel>
    public abstract int compareTo (ChatChannel other);

    /**
     * Converts this channel into a unique name that can be used as the name of the distributed
     * lock used when resolving the channel.
     */
    public abstract String getLockName ();

    // from interface DSet.Entry
    public Comparable<?> getKey ()
    {
        return this;
    }

    @Override
    public boolean equals (Object other)
    {
        return compareTo((ChatChannel)other) == 0;
    }

    @Override
    public abstract int hashCode ();
}
