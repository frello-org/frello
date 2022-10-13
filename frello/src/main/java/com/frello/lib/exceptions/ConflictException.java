package com.frello.lib.exceptions;

import lombok.Getter;
import lombok.NonNull;

public class ConflictException extends Exception {
    @Getter
    @NonNull
    private final String entityName;

    @Getter
    @NonNull
    private final String property;

    public ConflictException(String entityName, String property) {
        super(String.format(
                "ConflictException: Could not create `%s` due to conflicting `%s` property",
                entityName,
                property));
        this.entityName = entityName;
        this.property = property;
    }

    public ConflictException(String entityName, String property, String message) {
        super(String.format(
                "ConflictException: Could not create `%s` due to conflicting `%s` property (%s)",
                entityName,
                property,
                message));
        this.entityName = entityName;
        this.property = property;
    }
}
