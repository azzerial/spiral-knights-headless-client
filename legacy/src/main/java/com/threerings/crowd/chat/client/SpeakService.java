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

package com.threerings.crowd.chat.client;

import com.threerings.presents.client.InvocationService;
import com.threerings.presents.data.ClientObject;

/**
 * Provides a means by which "speaking" can be allowed among subscribers
 * of a particular distributed object.
 */
public interface SpeakService extends InvocationService<ClientObject>
{
    /**
     * Issues a request to speak "on" the distributed object via which
     * this speak service was provided.
     *
     * @param message the message to be spoken.
     * @param mode the "mode" of the message. This is an opaque value that
     * will be passed back down via the {@link ChatDirector} to the {@link
     * ChatDisplay} implementations which can interpret it in an
     * application specific manner. It's useful for differentiating
     * between regular speech, emotes, etc.
     */
    void speak (String message, byte mode);
}
