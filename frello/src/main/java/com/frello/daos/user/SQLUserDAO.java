package com.frello.daos.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.frello.lib.exceptions.ConflictException;
import org.postgresql.util.PSQLException;

import com.frello.lib.DB;
import com.frello.lib.exceptions.InternalException;
import com.frello.models.user.AdminActor;
import com.frello.models.user.ServiceConsumerActor;
import com.frello.models.user.ServiceProviderActor;
import com.frello.models.user.User;

public class SQLUserDAO implements UserDAO {
    @Override
    public Optional<User> user(UUID id) {
        return unsafeUserByColumnAndValue("id", id);
    }

    @Override
    public Optional<User> user(String username) {
        return unsafeUserByColumnAndValue("username", username);
    }

    private <T> Optional<User> unsafeUserByColumnAndValue(String column, T val) {
        if (!(column.equals("id") || column.equals("username"))) {
            throw new RuntimeException("Invalid column name");
        }

        // SAFETY: If not careful this could lead to an SQL Injection attack.
        // DO NOT ADD QUERY PARAMETERS HERE.
        var query = String.format("""
                        SELECT u.id, u.username, u.email, u.password_hash,
                            u.first_name, u.last_name,
                            u.is_deleted, u.deletion_time, u.creation_time,
                            a.is_enabled AS is_admin,
                            p.is_enabled AS is_provider,
                            c.is_enabled AS is_consumer
                        FROM frello.users AS u
                            LEFT JOIN frello.admin_actors AS a USING (id)
                            LEFT JOIN frello.service_provider_actors AS p USING (id)
                            LEFT JOIN frello.service_consumer_actors AS c USING (id)
                        WHERE u.%s = ? AND NOT u.is_deleted;
                        """,
                column);

        try (var conn = DB.getConnection()) {
            var stmt = conn.prepareStatement(query);
            stmt.setObject(1, val);

            var set = stmt.executeQuery();
            if (!set.next()) {
                return Optional.empty();
            }

            var id = set.getObject("id", UUID.class);
            var user = User.builder()
                    .id(id)
                    .username(set.getString("username"))
                    .email(set.getString("email"))
                    .passwordHash(set.getString("password_hash"))
                    .firstName(set.getString("first_name"))
                    .lastName(set.getString("last_name"))
                    .deleted(set.getBoolean("is_deleted"))
                    .deletionTime(set.getObject("deletion_time", OffsetDateTime.class))
                    .creationTime(set.getObject("creation_time", OffsetDateTime.class));
            if (set.getBoolean("is_admin")) {
                user.admin(new AdminActor(id));
            }
            if (set.getBoolean("is_consumer")) {
                user.consumer(new ServiceConsumerActor(id));
            }
            if (set.getBoolean("is_provider")) {
                user.provider(new ServiceProviderActor(id));
            }

            return Optional.of(user.build());
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    @Override
    public void create(User user) throws ConflictException {
        try {
            DB.inTransaction((conn) -> {
                PreparedStatement stmt;

                stmt = conn.prepareStatement("""
                        INSERT INTO frello.users (
                            id, username, password_hash, email,
                            first_name, last_name,
                            is_deleted, deletion_time, creation_time
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                        """);
                stmt.setObject(1, user.getId());
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getPasswordHash());
                stmt.setString(4, user.getEmail());
                stmt.setString(5, user.getFirstName());
                stmt.setString(6, user.getLastName());
                stmt.setBoolean(7, user.isDeleted());
                stmt.setObject(8, user.getDeletionTime());
                stmt.setObject(9, user.getCreationTime());
                DB.mustUpdate(stmt, 1);

                // Here goes duplication!
                if (user.isAdmin()) {
                    stmt = conn.prepareStatement("""
                                INSERT INTO frello.admin_actors
                                    (id, is_enabled) VALUES (?, true);
                            """);
                    stmt.setObject(1, user.getId());
                    DB.mustUpdate(stmt, 1);
                }
                if (user.isConsumer()) {
                    stmt = conn.prepareStatement("""
                                INSERT INTO frello.service_consumer_actors
                                    (id, is_enabled) VALUES (?, true);
                            """);
                    stmt.setObject(1, user.getId());
                    DB.mustUpdate(stmt, 1);
                }
                if (user.isProvider()) {
                    stmt = conn.prepareStatement("""
                                INSERT INTO frello.service_provider_actors
                                    (id, is_enabled) VALUES (?, true);
                            """);
                    stmt.setObject(1, user.getId());
                    DB.mustUpdate(stmt, 1);
                }

                return null;
            });
        } catch (PSQLException pgEx) {
            var constraint = Optional
                    .ofNullable(pgEx.getServerErrorMessage())
                    .flatMap((pg) -> Optional.ofNullable(pg.getConstraint()))
                    .orElseThrow(() -> new InternalException(pgEx));

            switch (constraint) {
                case "users_username_key" -> {
                    throw new ConflictException("user", "username");
                }
                case "users_email_key" -> {
                    throw new ConflictException("user", "email");
                }
                default -> {
                    throw new InternalException(pgEx);
                }
            }
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }
}
