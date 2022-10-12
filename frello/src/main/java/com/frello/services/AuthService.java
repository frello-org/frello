package com.frello.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.frello.daos.user.SQLUserDAO;
import com.frello.lib.Env;
import com.frello.lib.Hash;
import com.frello.models.user.User;
import com.frello.services.common.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthService {
    public LoginResponse login(LoginParams params) throws HttpException {
        var userDAO = new SQLUserDAO();

        var user = userDAO
                .user(params.getUsername())
                .orElseThrow(() -> new HttpException(401, "Invalid credentials"));

        if (!Hash.verify(user.getPasswordHash(), params.getPassword())) {
            throw new HttpException(401, "Invalid credentials");
        }

        var token = AuthJWT.issue(user);
        return new LoginResponse(user, token);
    }

    private static class AuthJWT {
        private static Algorithm algorithm = Algorithm.HMAC512(Env.get("JWT_SECRET"));

        public static String issue(User user) {
            return JWT.create()
                    .withIssuer("frello")
                    .withSubject(user.getId().toString())
                    .withClaim("username", user.getUsername())
                    .withClaim("isAdmin", user.isAdmin())
                    .withClaim("isConsumer", user.isConsumer())
                    .withClaim("isProvider", user.isProvider())
                    .sign(algorithm);
        }
    }

    @Data
    public static class LoginParams {
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class LoginResponse {
        private User user;
        private String token;
    }
}
