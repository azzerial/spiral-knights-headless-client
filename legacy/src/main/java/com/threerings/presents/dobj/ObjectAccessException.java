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

package com.threerings.presents.dobj;

/**
 * An object access exception is delivered when an object is not accessible to a requesting
 * subscriber for some reason or other. For some access exceptions, special derived classes exist
 * to communicate the error. For others, a message string explaining the access failure is
 * provided.
 */
public class ObjectAccessException extends Exception
{
    /**
     * Constructs a object access exception with the specified error message.
     */
    public ObjectAccessException (String message)
    {
        super(message);
    }

    /**
     * Constructs a object access exception with the specified error message and the chained
     * causing event.
     */
    public ObjectAccessException (String message, Exception cause)
    {
        super(message);
        initCause(cause);
    }

    /**
     * Constructs a object access exception with the specified chained causing event.
     */
    public ObjectAccessException (Exception cause)
    {
        initCause(cause);
    }
}
