package com.frello.daos.service;

import com.frello.daos.user.SQLUserDAO;
import com.frello.daos.user.UserDAO;
import com.frello.lib.DB;
import com.frello.lib.InternalException;
import com.frello.models.service.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class SQLServiceDAO implements ServiceDAO {
    private static final UserDAO userDAO = new SQLUserDAO();
    private static final ServiceRequestDAO serviceRequestDAO = new SQLServiceRequestDAO();

    @Override
    public Optional<Service> service(UUID id) {
        try (var conn = DB.getConnection()) {
            var stmt = conn.prepareStatement("""
                    SELECT id, state, request_id, provider_id, consumer_id, creation_time
                    FROM frello.services
                    WHERE id = ?;
                    """);
            stmt.setObject(1, id);

            var set = stmt.executeQuery();
            if (!set.next()) {
                return Optional.empty();
            }
            return Optional.of(map(set));
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    private Service map(ResultSet set) throws SQLException {
        var requestId = set.getObject("request_id", UUID.class);
        var consumerId = set.getObject("consumer_id", UUID.class);
        var providerId = set.getObject("provider_id", UUID.class);

        return Service.builder()
                .id(set.getObject("id", UUID.class))
                .state(Service.State.fromString(set.getString("state")))
                .requestId(requestId)
                .request(serviceRequestDAO.serviceRequest(requestId).get())
                .consumerId(consumerId)
                .consumer(userDAO.user(consumerId).get())
                .providerId(providerId)
                .provider(userDAO.user(providerId).get())
                .creationTime(set.getObject("creation_time", OffsetDateTime.class))
                .build();

    }
}
