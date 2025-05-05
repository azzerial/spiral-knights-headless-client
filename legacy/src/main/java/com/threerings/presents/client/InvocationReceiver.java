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

package com.threerings.presents.client;

import com.threerings.presents.dobj.DSet;

/**
 * Invocation notification receipt interfaces should be defined as
 * extending this interface. Actual notification receivers will implement
 * the requisite receiver interface definition and register themselves
 * with the {@link InvocationDirector} using the generated {@link
 * InvocationDecoder} class specific to the notification receiver
 * interface in question. For example:
 *
 * <pre>
 * public class FooDirector implements FooReceiver
 * {
 *     public FooDirector (PresentsContext ctx)
 *     {
 *         InvocationDirector idir = ctx.getClient().getInvocationDirector();
 *         idir.registerReceiver(new FooDecoder(this));
 *     }
 * }
 * </pre>
 *
 * @see InvocationDirector#registerReceiver
 */
public interface InvocationReceiver
{
    /**
     * Used to maintain a registry of invocation receivers that can be
     * used to convert (large) hash codes into (small) registration
     * numbers.
     */
    public static class Registration implements DSet.Entry
    {
        /** The unique hash code associated with this invocation receiver
         * class. */
        public String receiverCode;

        /** The unique id assigned to this invocation receiver class at
         * registration time. */
        public short receiverId;

        /** Creates and initializes a registration instance. */
        public Registration (String receiverCode, short receiverId)
        {
            this.receiverCode = receiverCode;
            this.receiverId = receiverId;
        }

        /** Creates a blank instance suitable for unserialization. */
        public Registration ()
        {
        }

        // documentation inherited from interface
        public Comparable<?> getKey ()
        {
            return receiverCode;
        }

        @Override
        public String toString ()
        {
            return "[" + receiverCode + " => " + receiverId + "]";
        }
    }
}
