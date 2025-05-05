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

/**
 * A ChatMessage that represents a message that came from the server and did not result from direct
 * user action.
 */
public class SystemMessage extends ChatMessage
{
    /** Attention level constant to indicate that this message is merely providing the user with
     * information. */
    public static final byte INFO      = 0;

    /** Attention level constant to indicate that this message is the result of a user action. */
    public static final byte FEEDBACK  = 1;

    /** Attention level constant to indicate that some action is required. */
    public static final byte ATTENTION = 2;

    /** The attention level of this message. */
    public byte attentionLevel;

    /**
     * Construct a SystemMessage.
     */
    public SystemMessage (String message, String bundle, byte attentionLevel)
    {
        super(message, bundle);
        this.attentionLevel = attentionLevel;
    }
}
