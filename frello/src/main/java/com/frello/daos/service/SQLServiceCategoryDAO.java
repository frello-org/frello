package com.frello.daos.service;

import com.frello.lib.DB;
import com.frello.lib.exceptions.InternalException;
import com.frello.models.service.ServiceCategory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class SQLServiceCategoryDAO implements ServiceCategoryDAO {
    @Override
    public List<ServiceCategory> categories() {
        try (var conn = DB.getConnection()) {
            var stmt = conn.prepareStatement("""
                    SELECT id, name, description, hex_css_color FROM frello.service_categories;
                    """);

            return fetch(stmt);
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    @Override
    public List<ServiceCategory> categoriesForRequest(UUID serviceRequestId) {
        try (var conn = DB.getConnection()) {
            var stmt = conn.prepareStatement("""
                    SELECT sc.id, sc.name, sc.description, sc.hex_css_color
                    FROM frello.service_categories sc
                        INNER JOIN frello.service_request_category AS rc ON rc.category_id = sc.id
                    WHERE rc.service_request_id = ?;
                    """);
            stmt.setObject(1, serviceRequestId);

            return fetch(stmt);
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }

    private List<ServiceCategory> fetch(PreparedStatement stmt) throws SQLException {
        return DB.collect(stmt.executeQuery(), (set) -> ServiceCategory.builder()
                .id(set.getObject("id", UUID.class))
                .name(set.getString("name"))
                .description(set.getString("description"))
                .hexCSSColor(set.getString("hex_css_color"))
                .build());
    }
}
