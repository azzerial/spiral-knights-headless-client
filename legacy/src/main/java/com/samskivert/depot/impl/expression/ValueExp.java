//
// Depot library - a Java relational persistence library
// https://github.com/threerings/depot/blob/master/LICENSE

package com.samskivert.depot.impl.expression;

import com.samskivert.depot.PersistentRecord;
import com.samskivert.depot.expression.FluentExp;
import com.samskivert.depot.impl.FragmentVisitor;

import java.util.Collection;

/**
 * A Java value that is bound as a parameter to the query, e.g. 1 or 'abc'.
 */
public class ValueExp<T> extends FluentExp<T>
{
    public ValueExp (T value)
    {
        _value = value;
    }

    // from SQLFragment
    public Object accept (FragmentVisitor<?> builder)
    {
        return builder.visit(this);
    }

    // from SQLFragment
    public void addClasses (Collection<Class<? extends PersistentRecord>> classSet)
    {
    }

    public T getValue ()
    {
        return _value;
    }

    @Override // from Object
    public String toString ()
    {
        return (_value instanceof Number) ? String.valueOf(_value) : ("'" + _value + "'");
    }

    /** The value to be bound to the SQL parameters. */
    protected T _value;
}
