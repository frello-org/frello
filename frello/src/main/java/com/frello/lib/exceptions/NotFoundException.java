package com.frello.lib.exceptions;

import lombok.Getter;
import lombok.NonNull;

public class NotFoundException extends Exception {
    @Getter
    @NonNull
    private final String entityName;

    @Getter
    @NonNull
    private final String property;

    @Getter
    private final Object value;

    public NotFoundException(String entityName, String property, Object value) {
        super(formatMessage(entityName, property, value));
        this.entityName = entityName;
        this.property = property;
        this.value = value;
    }

    public NotFoundException(String entityName, String property) {
        this(entityName, property, null);
    }

    private static String formatMessage(String entityName, String property, Object value) {
        var message = String.format(
                "NotFoundException: Could not find `%s` with the provided `%s`",
                entityName,
                property);
        if (value != null) {
            message += String.format(" defined as `%s`", value);
        }
        return message;
    }
}
