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

import com.samskivert.util.StringUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * An element updated event is dispatched when an element of an array field in a distributed object
 * is updated. It can also be constructed to request the update of an entry and posted to the
 * dobjmgr.
 *
 * @see DObjectManager#postEvent
 */
public class ElementUpdatedEvent extends NamedEvent
{
    /**
     * Constructs a new element updated event on the specified target object with the supplied
     * attribute name, element and index.
     *
     * @param targetOid the object id of the object whose attribute has changed.
     * @param name the name of the attribute (data member) for which an element has changed.
     * @param value the new value of the element (in the case of primitive types, the
     * reflection-defined object-alternative is used).
     * @param index the index in the array of the updated element.
     */
    public ElementUpdatedEvent (int targetOid, String name, Object value, int index)
    {
        super(targetOid, name);
        _value = value;
        _index = index;
    }

    /** For unserialization. */
    public ElementUpdatedEvent ()
    {
        super(0, null);
        // we can't allow our primary ctor to be called during unserialization, or it will wipe out
        // the hackery we do with _oldValue
    }

    /**
     * Returns the new value of the element.
     */
    public Object getValue ()
    {
        return _value;
    }

    /**
     * Returns the value of the element prior to the application of this event.
     */
    public Object getOldValue ()
    {
        return _oldValue;
    }

    /**
     * Returns the index of the element.
     */
    public int getIndex ()
    {
        return _index;
    }

    /**
     * Returns the new value of the element as a short. This will fail if the element in question
     * is not a short.
     */
    public short getShortValue ()
    {
        return ((Short)_value).shortValue();
    }

    /**
     * Returns the new value of the element as an int. This will fail if the element in question is
     * not an int.
     */
    public int getIntValue ()
    {
        return ((Integer)_value).intValue();
    }

    /**
     * Returns the new value of the element as a long. This will fail if the element in question is
     * not a long.
     */
    public long getLongValue ()
    {
        return ((Long)_value).longValue();
    }

    /**
     * Returns the new value of the element as a float. This will fail if the element in question
     * is not a float.
     */
    public float getFloatValue ()
    {
        return ((Float)_value).floatValue();
    }

    /**
     * Returns the new value of the element as a double. This will fail if the element in question
     * is not a double.
     */
    public double getDoubleValue ()
    {
        return ((Double)_value).doubleValue();
    }

    @Override
    public boolean alreadyApplied ()
    {
        return (_oldValue != UNSET_OLD_VALUE);
    }

    @Override
    public boolean applyToObject (DObject target)
        throws ObjectAccessException
    {
        if (!alreadyApplied()) {
            try {
                // fetch the array field from the object
                Field field = target.getClass().getField(_name);
                Class<?> ftype = field.getType();

                // sanity check
                if (!ftype.isArray()) {
                    String msg = "Requested to set element on non-array field.";
                    throw new Exception(msg);
                }

                // grab the previous value to provide to interested parties
                _oldValue = Array.get(field.get(target), _index);

                // we don't do any magical expansion or any funny business; the array should be big
                // enough to contain the value being updated or we'll throw an
                // ArrayIndexOutOfBoundsException
                Array.set(field.get(target), _index, _value);

            } catch (Exception e) {
                String msg = "Error updating element [field=" + _name + ", index=" + _index + "]";
                throw new ObjectAccessException(msg, e);
            }
        }
        return true;
    }

    @Override
    protected void notifyListener (Object listener)
    {
        if (listener instanceof ElementUpdateListener) {
            ((ElementUpdateListener)listener).elementUpdated(this);
        }
    }

    @Override
    protected void toString (StringBuilder buf)
    {
        buf.append("UPDATE:");
        super.toString(buf);
        buf.append(", value=");
        StringUtil.toString(buf, _value);
        buf.append(", index=").append(_index);
    }

    protected ElementUpdatedEvent setOldValue (Object oldValue)
    {
        _oldValue = oldValue;
        return this;
    }

    protected Object _value;
    protected int _index;
    protected transient Object _oldValue = UNSET_OLD_VALUE;
}
