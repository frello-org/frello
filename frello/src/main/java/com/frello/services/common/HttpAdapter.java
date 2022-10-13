package com.frello.services.common;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import com.frello.lib.json.Mapper;
import com.frello.models.user.User;
import com.frello.services.AuthService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class HttpAdapter {
    static ObjectMapper objectMapper = Mapper.getObjectMapper();

    private final Request req;
    private final Response res;

    public HttpAdapter(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    public <I, O> String adapt(Class<I> bodyType, Handler<I, O> handler) throws IOException {
        I requestBody;
        try {
            requestBody = objectMapper.readValue(req.body(), bodyType);
        } catch (IOException e) {
            res.status(400);
            return makeJSON(new UserError("Could not deserialize body", e.toString()));
        }
        return adaptResponse(() -> handler.apply(requestBody));
    }

    public <O> String adapt(Responder<O> responder) throws IOException {
        return adaptResponse(responder);
    }

    public <O> String adaptWithGuard(Handler<User, O> guardedContext) throws HttpException, IOException {
        return adapt(() -> guard(guardedContext));
    }

    public <O> O guard(Handler<User, O> guardedContext) throws HttpException {
        var token = req.headers("Authorization");
        if (token == null || token.isBlank()) {
            throw new HttpException(401, "Missing `Authorization` token");
        }

        var parts = token.split(" ");
        if (parts.length != 2 || !parts[0].equalsIgnoreCase("bearer")) {
            throw new HttpException(401, "Malformed `Authorization` token, expected `Bearer <token>`");
        }

        var user = AuthService.authorize(parts[1]);
        return guardedContext.apply(user);
    }

    private <T> String adaptResponse(Responder<T> responder) throws IOException {
        String json;
        int status = 200;
        try {
            var resp = responder.apply();
            json = makeJSON(resp);
            if (res.status() != status) {
                status = res.status();
            }
        } catch (HttpException e) {
            json = makeJSON(new UserError(e.getUserMessage()));
            status = e.getStatus();
        }

        res.header("Content-Type", "application/json");
        res.status(status);
        return json;
    }

    public static <T> String makeJSON(T data) throws IOException {
        var body = new ResponseBody<>(data);
        return Mapper.getObjectMapper().writeValueAsString(body);
    }

    @Data
    @AllArgsConstructor
    public static class ResponseBody<T> {
        T data;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class UserError {
        @NonNull
        private String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String description;
    }

    @FunctionalInterface
    public interface Handler<T, R> {
        R apply(T val) throws HttpException;
    }

    @FunctionalInterface
    public interface Responder<T> {
        T apply() throws HttpException;
    }
}
