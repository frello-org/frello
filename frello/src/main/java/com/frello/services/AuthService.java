package com.frello.services;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.frello.daos.user.SQLUserDAO;
import com.frello.daos.user.UserDAO;
import com.frello.lib.FrelloConfig;
import com.frello.lib.Hash;
import com.frello.lib.exceptions.ConflictException;
import com.frello.models.user.ServiceConsumerActor;
import com.frello.models.user.ServiceProviderActor;
import com.frello.models.user.User;
import com.frello.services.common.HttpException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

public class AuthService {
    private static final UserDAO userDAO = new SQLUserDAO();

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

    @Data
    @Builder
    @Jacksonized
    public static class LoginParams {
        @NonNull
        private String username;
        @NonNull
        private String password;
    }

    @Data
    @AllArgsConstructor
    @Builder
    @Jacksonized
    public static class LoginResponse {
        @NonNull
        private User user;
        @NonNull
        private String token;
    }

    public static void register(RegisterParams params) throws HttpException {
        var id = UUID.randomUUID();
        var user = User.builder()
                .id(id)
                .username(params.getUsername())
                .passwordHash(Hash.hash(params.getPassword()))
                .email(params.getEmail())
                .firstName(params.getFirstName())
                .lastName(params.getLastName())
                .creationTime(OffsetDateTime.now());
        if (params.isRegisterAsConsumer()) {
            user.consumer(new ServiceConsumerActor(id));
        }
        if (params.isRegisterAsProvider()) {
            user.provider(new ServiceProviderActor(id));
        }

        try {
            userDAO.create(user.build());
        } catch (ConflictException conflictEx) {
            throw new HttpException(conflictEx);
        }
    }

    @Data
    @Builder
    @Jacksonized
    public static class RegisterParams {
        @NonNull
        private String username;
        @NonNull
        private String password;
        @NonNull
        private String email;
        @NonNull
        private String firstName;
        @NonNull
        private String lastName;
        private boolean registerAsConsumer;
        private boolean registerAsProvider;
    }

    private static class AuthJWT {
        private static final String ISSUER = "frello";

        private static final Algorithm algorithm = Algorithm.HMAC512(FrelloConfig.JWT_SECRET);
        private static final JWTVerifier verifier = JWT.require(algorithm)
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
}
