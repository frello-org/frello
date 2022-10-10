package com.frello.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class Mapper {
    private static ObjectMapper objectMapper;

    private static ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
