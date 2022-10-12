package com.frello.services;

import com.frello.models.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserService {
    public static MeResponse me(User user) {
        return new MeResponse(user);
    }

    @Data
    @AllArgsConstructor
    public static class MeResponse {
        private User user;
    }
}
