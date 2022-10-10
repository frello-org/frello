package com.frello.lib;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DB {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        var host = env("PGHOST");
        var port = env("PGPORT");
        var database = env("PGDATABASE");
        var password = env("PGPASSWORD");
        var user = env("PGUSER");

        var url = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setMinIdle(8);
        ds.setMaxIdle(32);
        ds.setMaxOpenPreparedStatements(128);
    }

    /**
     * This method is unstable.
     */
    public static BasicDataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static String env(String name) {
        var value = System.getenv(name);
        if (value == null) {
            throw new RuntimeException(String.format(
                    "Missing required environment variable `%s`", name));
        }
        return value;
    }
}
