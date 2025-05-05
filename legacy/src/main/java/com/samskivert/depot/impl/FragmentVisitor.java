//
// Depot library - a Java relational persistence library
// https://github.com/threerings/depot/blob/master/LICENSE

package com.samskivert.depot.impl;

import com.samskivert.depot.Key;
import com.samskivert.depot.clause.*;
import com.samskivert.depot.expression.ColumnExp;
import com.samskivert.depot.impl.clause.CreateIndexClause;
import com.samskivert.depot.impl.clause.DeleteClause;
import com.samskivert.depot.impl.clause.DropIndexClause;
import com.samskivert.depot.impl.clause.UpdateClause;
import com.samskivert.depot.impl.expression.AggregateFun.*;
import com.samskivert.depot.impl.expression.ConditionalFun.Coalesce;
import com.samskivert.depot.impl.expression.ConditionalFun.Greatest;
import com.samskivert.depot.impl.expression.ConditionalFun.Least;
import com.samskivert.depot.impl.expression.DateFun.DatePart;
import com.samskivert.depot.impl.expression.DateFun.DateTruncate;
import com.samskivert.depot.impl.expression.DateFun.Now;
import com.samskivert.depot.impl.expression.IntervalExp;
import com.samskivert.depot.impl.expression.LiteralExp;
import com.samskivert.depot.impl.expression.NumericalFun.*;
import com.samskivert.depot.impl.expression.RandomExp;
import com.samskivert.depot.impl.expression.StringFun.*;
import com.samskivert.depot.impl.expression.ValueExp;
import com.samskivert.depot.impl.operator.*;
import com.samskivert.depot.operator.Case;
import com.samskivert.depot.operator.FullText;

/**
 * Enumerates visitation methods for every possible SQL expression type.
 */
public interface FragmentVisitor<T>
{
    public T visit (FieldDefinition fieldOverride);
    public T visit (FromOverride fromOverride);
    public T visit (MultiOperator<?> multiOperator);
    public T visit (BinaryOperator<?> binaryOperator);
    public T visit (IsNull isNull);
    public T visit (In in);
    public T visit (FullText.Match match);
    public T visit (FullText.Rank match);
    public T visit (ColumnExp<?> columnExp);
    public T visit (Not not);
    public T visit (Distinct distinct);
    public T visit (GroupBy groupBy);
    public T visit (ForUpdate forUpdate);
    public T visit (OrderBy orderBy);
    public T visit (Join join);
    public T visit (Limit limit);
    public T visit (LiteralExp<?> literal);
    public T visit (RandomExp random);
    public T visit (ValueExp<?> value);
    public T visit (IntervalExp interval);
    public T visit (WhereClause where);
    public T visit (Key.Expression key);
    public T visit (Exists exists);
    public T visit (SelectClause selectClause);
    public T visit (UpdateClause updateClause);
    public T visit (DeleteClause deleteClause);
    public T visit (InsertClause insertClause);
    public T visit (CreateIndexClause createIndexClause);
    public T visit (DropIndexClause dropIndexClause);
    public T visit (Case<?> caseExp);

    // Numerical
    public T visit (Abs<?> exp);
    public T visit (Ceil<?> exp);
    public T visit (Exp<?> exp);
    public T visit (Floor<?> exp);
    public T visit (Ln<?> exp);
    public T visit (Log10<?> exp);
    public T visit (Pi<?> exp);
    public T visit (Power<?> exp);
    public T visit (Random<?> exp);
    public T visit (Round<?> exp);
    public T visit (Sign<?> exp);
    public T visit (Sqrt<?> exp);
    public T visit (Trunc<?> exp);

    // String
    public T visit (Length exp);
    public T visit (Lower exp);
    public T visit (Position exp);
    public T visit (Substring exp);
    public T visit (Trim exp);
    public T visit (Upper exp);

    // Date
    public T visit (DatePart exp);
    public T visit (DateTruncate exp);
    public T visit (Now exp);

    // Aggregation
    public T visit (Average<?> exp);
    public T visit (Count exp);
    public T visit (Every exp);
    public T visit (Max<?> exp);
    public T visit (Min<?> exp);
    public T visit (Sum<?> exp);

    // Conditional
    public T visit (Coalesce<?> exp);
    public T visit (Greatest<?> exp);
    public T visit (Least<?> exp);
}
