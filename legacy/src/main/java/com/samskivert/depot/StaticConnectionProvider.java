//
// samskivert library - useful routines for java programs
// Copyright (C) 2001-2012 Michael Bayne, et al.
// http://github.com/samskivert/samskivert/blob/master/COPYING

package com.samskivert.depot;

import com.google.common.base.Strings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.samskivert.depot.Log.log;

/**
 * The static connection provider generates JDBC connections based on configuration information
 * provided via a properties file. It does no connection pooling and always returns the same
 * connection for a particular identifier (unless that connection need be closed because of a
 * connection failure, in which case it opens a new one the next time the connection is requested).
 *
 * <p> The configuration properties file should contain the following information:
 *
 * <pre>
 * IDENT.driver=[jdbc driver class]
 * IDENT.url=[jdbc driver url]
 * IDENT.username=[jdbc username]
 * IDENT.password=[jdbc password]
 *
 * [...]
 * </pre>
 *
 * Where <code>IDENT</code> is the database identifier for a particular database connection. When a
 * particular database identifier is requested, the configuration information will be fetched from
 * the properties.
 *
 * <p> Additionally, a default set of properties can be provided using the identifier
 * <code>default</code>. Values not provided for a specific identifier will be sought from the
 * defaults. For example:
 *
 * <pre>
 * default.driver=[jdbc driver class]
 * default.url=[jdbc driver class]
 *
 * IDENT1.username=[jdbc username]
 * IDENT1.password=[jdbc password]
 *
 * IDENT2.username=[jdbc username]
 * IDENT2.password=[jdbc password]
 *
 * [...]
 * </pre>
 */
public class StaticConnectionProvider implements ConnectionProvider
{
    /** Creates a provider for testing, using HSQLDB. */
    public static ConnectionProvider forTest (String dbname) {
        Properties props = new Properties();
        props.setProperty("default.driver", "org.hsqldb.jdbcDriver");
        props.setProperty("default.username", "sa");
        props.setProperty("default.password", "none");
        props.setProperty("default.url", "jdbc:hsqldb:mem:" + dbname);
        return new StaticConnectionProvider(props);
    }

    /**
     * Constructs a static connection provider which will load its configuration from a properties
     * file accessible via the classpath of the running application and identified by the specified
     * path.
     *
     * @param propPath the path (relative to the classpath) to the properties file that will be
     * used for configuration information.
     *
     * @exception IOException thrown if an error occurs locating or loading the specified
     * properties file.
     */
    public StaticConnectionProvider (String propPath) throws IOException
    {
        this(loadProperties(propPath, ConnectionProvider.class.getClassLoader()));
    }

    /**
     * Constructs a static connection provider which will fetch its configuration information from
     * the specified properties object.
     *
     * @param props the configuration for this connection provider.
     */
    public StaticConnectionProvider (Properties props)
    {
        _props = props;
    }

    // from ConnectionProvider
    public String getURL (String ident)
    {
        Properties props = getSubProperties(_props, ident, DEFAULTS_KEY);
        return props.getProperty("url");
    }

    // from ConnectionProvider
    public Connection getConnection (String ident, boolean readOnly)
    {
        return getMapping(ident, readOnly).getConnection(ident);
    }

    // from ConnectionProvider
    public void releaseConnection (String ident, boolean readOnly, Connection conn)
    {
        // nothing to do here, all is well
    }

    // from ConnectionProvider
    public void connectionFailed (
        String ident, boolean readOnly, Connection conn, SQLException error)
    {
        String mapkey = ident + ":" + readOnly;
        Mapping conmap = _idents.get(mapkey);
        if (conmap == null) {
            log.warning("Unknown connection failed!?", "key", mapkey);
        } else {
            conmap.closeConnection(ident);
        }
    }

    // from ConnectionProvider
    public Connection getTxConnection (String ident)
    {
        return getMapping(ident, false).openConnection(ident, false);
    }

    // from ConnectionProvider
    public void releaseTxConnection (String ident, Connection conn)
    {
        close(conn, ident);
    }

    // from ConnectionProvider
    public void txConnectionFailed (String ident, Connection conn, SQLException error)
    {
        close(conn, ident);
    }

    // from ConnectionProvider
    public void shutdown ()
    {
        // close all of the connections
        for (Map.Entry<String, Mapping> entry : _keys.entrySet()) {
            entry.getValue().closeConnection(entry.getKey());
        }

        // clear out our mapping tables
        _keys.clear();
        _idents.clear();
    }

    protected Mapping getMapping (String ident, boolean readOnly) {
        String mapkey = ident + ":" + readOnly;
        Mapping conmap = _idents.get(mapkey);
        if (conmap != null) return conmap;

        Properties props = getSubProperties(_props, ident, DEFAULTS_KEY);
        Info info = new Info(ident, props);

        // if this is a read-only connection, we cache connections by username+url+readOnly to
        // avoid making more that one connection to a particular database server
        String key = info.username + "@" + info.url + ":" + readOnly;
        conmap = _keys.get(key);
        if (conmap == null) {
            log.debug("Creating " + key + " for " + ident + ".");
            _keys.put(key, conmap = new Mapping(key, info, readOnly));

        } else {
            log.debug("Reusing " + key + " for " + ident + ".");
        }

        // cache the connection
        _idents.put(mapkey, conmap);

        return conmap;
    }

    protected static void close (Connection conn, String ident) {
        try {
            conn.close();
        } catch (SQLException sqe) {
            log.warning("Error closing failed connection", "ident", ident, "error", sqe);
        }
    }

    protected static Properties loadProperties (String propPath, ClassLoader loader)
    throws IOException {
        InputStream in = loader.getResourceAsStream(propPath);
        if (in == null) throw new FileNotFoundException(propPath);
        Properties props = new Properties();
        props.load(in);
        in.close();
        return props;
    }

    protected static Properties getSubProperties (Properties source, String pref, String defPref)
    {
        Properties defs = new Properties();
        extractProperties(source, defs, defPref);
        Properties dest = new Properties(defs);
        extractProperties(source, dest, pref);
        return dest;
    }

    protected static void extractProperties (Properties source, Properties dest, String prefix)
    {
        // extend the prefix to contain a dot
        prefix = prefix + ".";
        int preflen = prefix.length();

        // scan the source properties
        Enumeration<?> names = source.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String)names.nextElement();
            // skip unrelated properties
            if (!name.startsWith(prefix)) {
                continue;
            }
            // insert the value into the new properties minus the prefix
            dest.put(name.substring(preflen), source.getProperty(name));
        }
    }

    protected static class Info {
        public final String ident, driver, url, username, password;
        public final Boolean autoCommit;

        public Info (String ident, Properties props) {
            this.ident = ident;
            this.driver = requireProp(props, "driver", "No driver class specified");
            this.url = requireProp(props, "url", "No driver URL specified");
            this.username = requireProp(props, "username", "No driver username specified");
            this.password = props.getProperty("password", "");
            String ac = props.getProperty("autocommit");
            this.autoCommit = (ac == null) ? null : Boolean.valueOf(ac);
        }

        protected String requireProp (Properties props, String name, String errmsg) {
            String value = Strings.nullToEmpty(props.getProperty(name)).trim();
            if (value.isEmpty()) {
                errmsg = "Unable to get connection. " + errmsg + " [ident=" + ident + "]";
                throw new DatabaseException(errmsg);
            }
            return value;
        }
    }

    /** Contains information on a particular connection to which any number of database identifiers
      * can be mapped. */
    protected static class Mapping
    {
        /** The combination of username and JDBC url that uniquely identifies our database
         * connection. */
        public final String key;

        public Mapping (String key, Info info, boolean readOnly) {
            this.key = key;
            _info = info;
            _readOnly = readOnly;
        }

        /** Returns the main connection for this mapping, (re)opening it if necessary. */
        public Connection getConnection (String ident) {
            if (_conn == null) _conn = openConnection(ident, _info.autoCommit);
            return _conn;
        }

        /** Opens and returns a new connection to this mapping's database. */
        public Connection openConnection (String ident, Boolean autoCommit) {
            // create an instance of the driver
            Driver jdriver;
            try {
                jdriver = (Driver)Class.forName(_info.driver).newInstance();
            } catch (Exception e) {
                throw new DatabaseException(
                  "Error loading driver [class=" + _info.driver + "].", e);
            }

            // create the connection
            Connection conn;
            try {
                Properties props = new Properties();
                props.put("user", _info.username);
                props.put("password", _info.password);
                conn = jdriver.connect(_info.url, props);

            } catch (SQLException sqe) {
                throw new DatabaseException(
                    "Error creating database connection [driver=" + _info.driver +
                    ", url=" + _info.url + ", username=" + _info.username + "].", sqe);
            }

            // if we were requested to configure auto-commit, then do so
            if (autoCommit != null) {
                try {
                    conn.setAutoCommit(autoCommit);
                } catch (SQLException sqe) {
                    close(conn, ident);
                    throw new DatabaseException(
                        "Failed to configure auto-commit [key=" + key + ", ident=" + ident +
                        ", autoCommit=" + autoCommit + "].", sqe);
                }
            }

            // make the connection read-only to let the JDBC driver know that it can and should
            // use the read-only mirror(s)
            if (_readOnly) {
                try {
                    conn.setReadOnly(true);
                } catch (SQLException sqe) {
                    close(conn, ident);
                    throw new DatabaseException(
                        "Failed to make connection read-only [key=" + key + ", ident=" + ident +
                        "].", sqe);
                }
            }
            return conn;
        }

        /**
         * Closes the main connection for this mapping, causing it to be reopened on the next call
         * to {@link #getConnection}.
         * @param ident the ident on behalf of which we are operating.
         */
        public void closeConnection (String ident) {
            if (_conn != null) {
                close(_conn, ident);
                _conn = null;
            }
        }

        protected final Info _info;
        protected final boolean _readOnly;
        protected Connection _conn;
    }

    /** Our configuration in the form of a properties object. */
    protected Properties _props;

    /** A mapping from database identifier to connection records. */
    protected HashMap<String,Mapping> _idents = new HashMap<String,Mapping>();

    /** A mapping from connection key to connection records. */
    protected HashMap<String,Mapping> _keys = new HashMap<String,Mapping>();

    /** The key used as defaults for the database definitions. */
    protected static final String DEFAULTS_KEY = "default";
}
