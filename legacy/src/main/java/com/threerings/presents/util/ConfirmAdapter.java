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

/**
 * Adapts the response from a {@link ResultListener} to a {@link ConfirmListener} if the failure is
 * an instance of {@link InvocationException} the message will be passed on to the confirm
 * listener, otherwise they will be provided with {@link InvocationCodes#INTERNAL_ERROR}.
 */
public class ConfirmAdapter extends IgnoreConfirmAdapter<Void>
{
    /**
     * Creates an adapter with the supplied listener.
     */
    public ConfirmAdapter (ConfirmListener listener)
    {
        super(listener);
    }
}