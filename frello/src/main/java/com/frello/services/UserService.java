package com.frello.services;

import com.frello.models.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

public class UserService {
    public static MeResponse me(User user) {
        return new MeResponse(user);
    }

    @Data
    @AllArgsConstructor
    @Builder
    @Jacksonized
    public static class MeResponse {
        @NonNull
        private User user;
    }
}
