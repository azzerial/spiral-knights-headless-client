//
// Depot library - a Java relational persistence library
// https://github.com/threerings/depot/blob/master/LICENSE

package com.samskivert.depot.impl.expression;

import com.samskivert.depot.PersistentRecord;
import com.samskivert.depot.expression.SQLExpression;
import com.samskivert.depot.impl.FragmentVisitor;

import java.util.Collection;

/**
 * An expression for things we don't support natively, e.g. COUNT(*).
 */
public class LiteralExp<T>
    implements SQLExpression<T>
{
    public LiteralExp (String text)
    {
        super();
        _text = text;
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

    public String getText ()
    {
        return _text;
    }

    @Override
    public String toString ()
    {
        return _text;
    }

    /** The literal text of this expression, e.g. COUNT(*) */
    protected String _text;
}
