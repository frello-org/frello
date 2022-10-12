package com.frello.daos.service;

import com.frello.lib.DB;
import com.frello.lib.InternalException;
import com.frello.models.service.ServiceCategory;

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

            return DB.collect(stmt.executeQuery(), (set) -> ServiceCategory.builder()
                    .id(set.getObject("id", UUID.class))
                    .name(set.getString("name"))
                    .description(set.getString("description"))
                    .hexCSSColor(set.getString("hex_css_color"))
                    .build()
            );
        } catch (SQLException sqlEx) {
            throw new InternalException(sqlEx);
        }
    }
}
