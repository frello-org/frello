package com.frello.daos.service;

import com.frello.daos.user.SQLUserDAO;
import com.frello.daos.user.UserDAO;
import com.frello.lib.DB;
import com.frello.lib.InternalException;
import com.frello.models.service.ServiceRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLServiceRequestDAO implements ServiceRequestDAO {
    private static UserDAO userDAO = new SQLUserDAO();
    private static ServiceCategoryDAO categoryDAO = new SQLServiceCategoryDAO();

    @Override
    public Optional<ServiceRequest> serviceRequest(UUID id) {
        try (var conn = DB.getConnection()) {
            var stmt = conn.prepareStatement("""
                    SELECT
                        id, consumer_id, expected_price, title,
                        raw_markdown_page_body, parsed_html_page_body,
                        is_deleted, deletion_time, creation_time
                    FROM frello.service_requests
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

    @Override
    public List<ServiceRequest> serviceRequests() {
        try (var conn = DB.getConnection()) {
            var stmt = conn.prepareStatement("""
                    SELECT
                        id, consumer_id, expected_price, title,
                        raw_markdown_page_body, parsed_html_page_body,
                        is_deleted, deletion_time, creation_time
                    FROM frello.service_requests;
                    """);

            return DB.collect(stmt.executeQuery(), this::map);
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    private ServiceRequest map(ResultSet set) throws SQLException {
        var id = set.getObject("id", UUID.class);
        var consumerId = set.getObject("consumer_id", UUID.class);

        return ServiceRequest.builder()
                .id(id)
                .consumerId(consumerId)
                .categories(categoryDAO.categoriesForRequest(id))
                // FIXME: Fix O(n) issue.
                .consumer(userDAO.user(consumerId).get())
                .expectedPrice(set.getBigDecimal("expected_price"))
                .title(set.getString("title"))
                .rawMarkdownPageBody(set.getString("raw_markdown_page_body"))
                .parsedHTMLPageBody(set.getString("parsed_html_page_body"))
                .deleted(set.getBoolean("is_deleted"))
                .deletionTime(set.getObject("deletion_time", OffsetDateTime.class))
                .creationTime(set.getObject("creation_time", OffsetDateTime.class))
                .build();
    }
}
