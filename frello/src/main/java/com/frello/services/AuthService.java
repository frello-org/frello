package com.frello.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.frello.daos.user.SQLUserDAO;
import com.frello.daos.user.UserDAO;
import com.frello.lib.Env;
import com.frello.lib.Hash;
import com.frello.models.user.User;
import com.frello.services.common.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthService {
    private static UserDAO userDAO = new SQLUserDAO();

    public static User authorize(String token) throws HttpException {
        var userId = AuthJWT
                .verifyAndGetUserId(token)
                .orElseThrow(() -> new HttpException(401, "Invalid credentials"));

        return userDAO
                .user(userId)
                .orElseThrow(() -> new HttpException(401, "Invalid credentials"));
    }

    public static LoginResponse login(LoginParams params) throws HttpException {
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
        private static final String ISSUER = "frello";

        private static Algorithm algorithm = Algorithm.HMAC512(Env.get("JWT_SECRET"));
        private static JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        public static Optional<UUID> verifyAndGetUserId(String jwt) {
            try {
                var decoded = verifier.verify(jwt);
                var userId = UUID.fromString(decoded.getSubject());
                return Optional.of(userId);
            } catch (JWTVerificationException e) {
                return Optional.empty();
            }
        }

        public static String issue(User user) {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(Instant.now().plus(Duration.ofDays(1)))
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
