package com.frello.services.common;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import com.frello.lib.json.Mapper;
import com.frello.models.user.User;
import com.frello.services.AuthService;

import lombok.AllArgsConstructor;
import lombok.Data;

public class HttpAdapter {
    static ObjectMapper objectMapper = Mapper.getObjectMapper();

    private Request req;
    private Response res;

    public HttpAdapter(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    public <I, O> String adapt(Class<I> bodyType, Handler<I, O> handler) throws IOException {
        var requestBody = objectMapper.readValue(req.body(), bodyType);
        return adaptResponse(() -> handler.apply(requestBody));
    }

    public <O> String adapt(Responder<O> responder) throws IOException {
        return adaptResponse(() -> responder.apply());
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

    private static <T> String makeJSON(T data) throws IOException {
        var body = new ResponseBody<>(data);
        return Mapper.getObjectMapper().writeValueAsString(body);
    }

    @Data
    @AllArgsConstructor
    public static class ResponseBody<T> {
        T data;
    }

    @Data
    @AllArgsConstructor
    private static class UserError {
        private String message;
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
