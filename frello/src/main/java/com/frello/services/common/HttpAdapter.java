package com.frello.services.common;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import spark.Request;
import spark.Response;

import com.frello.lib.json.Mapper;

public class HttpAdapter {
    static ObjectMapper objectMapper = Mapper.getObjectMapper();

    public static <T, U> String adapt(Request req, Response res, Class<T> bodyType, Handler<T, U> handler)
            throws IOException {
        var rawBody = req.body();
        var parsedBody = objectMapper.readValue(rawBody, bodyType);

        int status = 200;
        String json;
        try {
            var responseBody = handler.apply(parsedBody);
            json = makeResponseBody(responseBody);
        } catch (HttpException e) {
            var error = new ErrorResponseBody(e.getUserMessage());
            json = makeResponseBody(error);
            status = e.getStatus();
        }

        res.header("Content-Type", "application/json");
        res.status(status);
        return json;
    }

    private static <T> String makeResponseBody(T data) throws IOException {
        var body = new ResponseBody<>(data);
        return objectMapper.writeValueAsString(body);
    }

    @Data
    @AllArgsConstructor
    private static class ResponseBody<T> {
        T data;
    }

    @Data
    @AllArgsConstructor
    private static class ErrorResponseBody {
        private String message;
    }

    @FunctionalInterface
    public interface Handler<T, R> {
        R apply(T t) throws HttpException;
    }
}
