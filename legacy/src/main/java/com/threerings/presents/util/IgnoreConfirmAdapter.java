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

package com.threerings.presents.util;

import com.samskivert.util.ResultListener;
import com.threerings.presents.client.InvocationService.ConfirmListener;
import com.threerings.presents.data.InvocationCodes;
import com.threerings.presents.server.InvocationException;

import static com.threerings.presents.Log.log;

/**
 * Adapts the response from a {@link ResultListener} to a {@link ConfirmListener} wherein the
 * result is ignored. If the failure is an instance fo {@link InvocationException} the message will
 * be passed on to the confirm listener, otherwise they will be provided with {@link
 * InvocationCodes#INTERNAL_ERROR}.
 *
 * @param <T> the type of result expected by the listener.
 */
public class IgnoreConfirmAdapter<T> implements ResultListener<T>
{
    /**
     * Creates an adapter with the supplied listener.
     */
    public IgnoreConfirmAdapter (ConfirmListener listener)
    {
        _listener = listener;
    }

    // documentation inherited from interface
    public void requestCompleted (T result)
    {
        _listener.requestProcessed();
    }

    // documentation inherited from interface
    public void requestFailed (Exception cause)
    {
        if (cause instanceof InvocationException) {
            _listener.requestFailed(cause.getMessage());
        } else {
            log.warning(cause);
            _listener.requestFailed(InvocationCodes.INTERNAL_ERROR);
        }
    }

    protected ConfirmListener _listener;
}