package com.frello.daos.service;

import com.frello.daos.user.SQLUserDAO;
import com.frello.daos.user.UserDAO;
import com.frello.lib.DB;
import com.frello.lib.InternalException;
import com.frello.models.service.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLServiceDAO implements ServiceDAO {
    private static final UserDAO userDAO = new SQLUserDAO();
    private static final ServiceRequestDAO serviceRequestDAO = new SQLServiceRequestDAO();

    @Override
    public Optional<Service> service(UUID id) {
        try (var conn = DB.getConnection()) {
            var set = unsafeQueryServicesByIdColumn(conn, "id", id);
            if (!set.next()) {
                return Optional.empty();
            }
            return Optional.of(map(set));
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    @Override
    public List<Service> userConsumedServices(UUID consumerId) {
        return unsafeLoadManyByColumnName("consumer_id", consumerId);
    }

    @Override
    public List<Service> userProvidedServices(UUID providerId) {
        return unsafeLoadManyByColumnName("provider_id", providerId);
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

    private ResultSet unsafeQueryServicesByIdColumn(Connection conn, String column, UUID id)
            throws SQLException {
        if (column != "id" && column != "consumer_id" && column != "provider_id") {
            throw new RuntimeException("Invalid column name");
        }

        // SAFETY: If not careful this could lead to an SQL Injection attack.
        // DO NOT ADD QUERY PARAMETERS HERE.
        var query = String.format("""
                SELECT id, state, request_id, provider_id, consumer_id, creation_time
                FROM frello.services
                WHERE %s = ?;
                """,
                column);

        var stmt = conn.prepareStatement(query);
        stmt.setObject(1, id);
        return stmt.executeQuery();
    }

    private List<Service> unsafeLoadManyByColumnName(String column, UUID id) {
        try (var conn = DB.getConnection()) {
            var sets = unsafeQueryServicesByIdColumn(conn, column, id);
            return DB.collect(sets, this::map);
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }
}
