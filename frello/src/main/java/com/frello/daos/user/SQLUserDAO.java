package com.frello.daos.user;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.frello.lib.DB;
import com.frello.lib.InternalException;
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
                    SELECT u.id, u.username, u.first_name, u.last_name,
                    u.is_deleted, u.deletion_time, u.creation_time,
                    a.is_enabled AS is_admin,
                    p.is_enabled AS is_provider,
                    c.is_enabled AS is_consumer
                FROM frello.users AS u
                    LEFT JOIN frello.admin_actors AS a USING (id)
                    LEFT JOIN frello.service_provider_actors AS p USING (id)
                    LEFT JOIN frello.service_consumer_actors AS c USING (id)
                WHERE u.%s = ?;
                """,
                column); // <----- (!) (!) (!)

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
                    .firstName(set.getString("first_name"))
                    .lastName(set.getString("last_name"))
                    .deleted(set.getBoolean("is_deleted"))
                    .deletionTime(Optional.ofNullable(set.getObject("deletion_time", OffsetDateTime.class)))
                    .creationTime(set.getObject("creation_time", OffsetDateTime.class));
            if (set.getBoolean("is_admin")) {
                user.admin(Optional.of(new AdminActor(id)));
            }
            if (set.getBoolean("is_consumer")) {
                user.consumer(Optional.of(new ServiceConsumerActor(id)));
            }
            if (set.getBoolean("is_provider")) {
                user.provider(Optional.of(new ServiceProviderActor(id)));
            }

            return Optional.of(user.build());
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

}
