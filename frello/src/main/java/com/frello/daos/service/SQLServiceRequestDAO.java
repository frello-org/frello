package com.frello.daos.service;

import com.frello.daos.user.SQLUserDAO;
import com.frello.daos.user.UserDAO;
import com.frello.lib.DB;
import com.frello.lib.exceptions.InternalException;
import com.frello.lib.exceptions.NotFoundException;
import com.frello.models.service.ServiceRequest;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLServiceRequestDAO implements ServiceRequestDAO {
    private static final UserDAO userDAO = new SQLUserDAO();
    private static final ServiceCategoryDAO categoryDAO = new SQLServiceCategoryDAO();

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

    @Override
    public void createIgnoringCategories(ServiceRequest serviceRequest) {
        try (var conn = DB.getConnection()) {
            rawCreateServiceRequest(conn, serviceRequest);
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    @Override
    public void createWithCategories(ServiceRequest serviceRequest, List<UUID> categoryIDs) throws NotFoundException {
        try {
            DB.inTransaction((conn) -> {
                rawCreateServiceRequest(conn, serviceRequest);
                for (var categoryId : categoryIDs) {
                    associateCategoryToRequest(conn, serviceRequest.getId(), categoryId);
                }
                return null;
            });
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

    private void rawCreateServiceRequest(Connection conn, ServiceRequest sr) throws SQLException {
        var stmt = conn.prepareStatement("""
                INSERT INTO frello.service_requests (
                    id, consumer_id, expected_price, title,
                    raw_markdown_page_body, parsed_html_page_body,
                    is_deleted, deletion_time, creation_time
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """);
        stmt.setObject(1, sr.getId());
        stmt.setObject(2, sr.getConsumerId());
        stmt.setBigDecimal(3, sr.getExpectedPrice());
        stmt.setString(4, sr.getTitle());
        stmt.setString(5, sr.getRawMarkdownPageBody());
        stmt.setString(6, sr.getParsedHTMLPageBody());
        stmt.setBoolean(7, sr.isDeleted());
        stmt.setObject(8, sr.getDeletionTime());
        stmt.setObject(9, sr.getCreationTime());
        DB.mustUpdate(stmt, 1);
    }

    private void associateCategoryToRequest(Connection conn, UUID requestId, UUID categoryId)
            throws SQLException, NotFoundException {
        try {
            var stmt = conn.prepareStatement("""
                    INSERT INTO frello.service_request_category
                        (service_request_id, category_id) VALUES (?, ?);
                    """);
            stmt.setObject(1, requestId);
            stmt.setObject(2, categoryId);
            DB.mustUpdate(stmt, 1);
        } catch (PSQLException pgEx) {
            var constraint = DB.tryGetConstraint(pgEx)
                    .orElseThrow(() -> new InternalException(pgEx));
            switch (constraint) {
                case "service_request_category_service_request_id_fkey" -> {
                    throw new NotFoundException("ServiceRequest", "id", requestId);
                }
                case "service_request_category_category_id_fkey" -> {
                    throw new NotFoundException("ServiceCategory", "id", categoryId);
                }
            }
        }

    }
}
