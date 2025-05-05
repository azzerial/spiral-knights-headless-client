//
// Depot library - a Java relational persistence library
// https://github.com/threerings/depot/blob/master/LICENSE

package com.samskivert.depot.clause;

import com.samskivert.depot.PersistentRecord;
import com.samskivert.depot.impl.FragmentVisitor;

import java.util.Collection;

/**
 *  Represents a FOR UPDATE clause.
 */
public class ForUpdate implements QueryClause
{
    // from SQLExpression
    public Object accept (FragmentVisitor<?> builder)
    {
        return builder.visit(this);
    }

    // from SQLExpression
    public void addClasses (Collection<Class<? extends PersistentRecord>> classSet)
    {
    }
}
