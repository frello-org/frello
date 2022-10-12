package com.frello.services.common;

import java.util.UUID;

public class External {
    public static UUID uuid(String raw) throws HttpException {
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException e) {
            throw new HttpException(400, e.getMessage());
        }
    }
}
