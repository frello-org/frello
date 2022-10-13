package com.frello.lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.frello.lib.exceptions.InternalException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;

public class DB {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        var host = FrelloConfig.PGHOST;
        var port = FrelloConfig.PGPORT;
        var user = FrelloConfig.PGUSER;
        var password = FrelloConfig.PGPASSWORD;
        var database = FrelloConfig.PGDATABASE;

        var url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);

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

    public static <R, E extends Exception> R inTransaction(TransactionContext<R, E> ctx)
            throws E, SQLException {
        try (var conn = getConnection()) {
            try {
                conn.setAutoCommit(false);
                var ret = ctx.apply(conn);
                conn.commit();
                return ret;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public static void mustUpdate(PreparedStatement stmt, int count) throws SQLException {
        var realCount = stmt.executeUpdate();
        if (realCount != count) {
            var message = String.format("Should have updated %d rows, got %d", count, realCount);
            throw new InternalException(message);
        }
    }

    public static <T> List<T> collect(ResultSet set, Mapper<T> mapper) throws SQLException {
        var list = new ArrayList<T>();
        while (set.next()) {
            list.add(mapper.apply(set));
        }
        return list;
    }

    public static Optional<String> tryGetConstraint(PSQLException pgEx) {
        return Optional
                .ofNullable(pgEx.getServerErrorMessage())
                .flatMap((pg) -> Optional.ofNullable(pg.getConstraint()));
    }

    @FunctionalInterface
    public interface Mapper<R> {
        R apply(ResultSet set) throws SQLException;
    }

    @FunctionalInterface
    public interface TransactionContext<R, E extends Exception> {
        R apply(Connection conn) throws E;
    }
}
