//
// Depot library - a Java relational persistence library
// https://github.com/threerings/depot/blob/master/LICENSE

package com.samskivert.depot.impl.expression;

import com.samskivert.depot.PersistentRecord;
import com.samskivert.depot.expression.FluentExp;
import com.samskivert.depot.expression.SQLExpression;

import java.util.Collection;

public abstract class ArgumentExp<T> extends FluentExp<T>
{
    protected ArgumentExp (SQLExpression<?>... args)
    {
        _args = args;
    }

    public void addClasses (Collection<Class<? extends PersistentRecord>> classSet)
    {
        for (SQLExpression<?> arg : _args) {
            arg.addClasses(classSet);
        }
    }

    public SQLExpression<?>[] getArgs ()
    {
        return _args;
    }

    protected SQLExpression<?>[] _args;
}
