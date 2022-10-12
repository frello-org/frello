package com.frello.lib;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DB {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        var host = Env.get("PGHOST");
        var port = Env.get("PGPORT");
        var database = Env.get("PGDATABASE");
        var password = Env.get("PGPASSWORD");
        var user = Env.get("PGUSER");

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
}
