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

/**
 * A session observer is registered with the client instance to be notified when the client
 * establishes and ends their session with the server.
 *
 * @see ClientObserver
 */
public interface SessionObserver
{
    /**
     * Called immediately before a logon is attempted.
     */
    void clientWillLogon (Client client);

    /**
     * Called after the client successfully connected to and authenticated with the server. The
     * entire object system is up and running by the time this method is called.
     */
    void clientDidLogon (Client client);

    /**
     * For systems that allow switching screen names after logon, this method is called whenever a
     * screen name change takes place to report that the client object has been replaced to
     * potential client-side subscribers.
     */
    void clientObjectDidChange (Client client);

    /**
     * Called after the client has been logged off of the server and has disconnected.
     */
    void clientDidLogoff (Client client);
}
