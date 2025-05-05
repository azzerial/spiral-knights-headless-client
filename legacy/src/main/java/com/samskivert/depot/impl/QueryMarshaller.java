//
// Depot library - a Java relational persistence library
// https://github.com/threerings/depot/blob/master/LICENSE

package com.samskivert.depot.impl;

import com.samskivert.depot.Key;
import com.samskivert.depot.PersistentRecord;
import com.samskivert.depot.expression.SQLExpression;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Marshalls the results for a query.
 */
public interface QueryMarshaller<T extends PersistentRecord,R>
{
    /**
     * Returns the name of the table in which persistent instances of our class are stored. By
     * default this is the classname of the persistent object without the package.
     */
    String getTableName ();

    /**
     * Returns the expressions being selected for this query.
     */
    SQLExpression<?>[] getSelections ();

    /**
     * Extracts the primary key from the supplied object.
     */
    Key<T> getPrimaryKey (Object object);

    /**
     * Creates an instance of the query result from the supplied result set.
     */
    R createObject (ResultSet rs) throws SQLException;
}
