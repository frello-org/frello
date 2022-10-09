package com.frello.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Mapper {
    private static ObjectMapper objectMapper;

    private static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(new InstantSerdeModule());
    }

    public static ObjectMapper getObjectMapper() {
        // https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java
        ObjectMapper current = objectMapper;
        if (current != null) {
            return current;
        }
        synchronized (Mapper.class) {
            if (objectMapper == null) {
                objectMapper = createObjectMapper();
            }
            return objectMapper;
        }
    }
}
