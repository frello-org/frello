package com.frello;

import static spark.Spark.*;

import com.frello.services.AuthService;
import com.frello.services.common.HttpAdapter;

public class App {
    public static void main(String[] args) {
        port(3333);

        post("/auth/login", (req, res) -> {
            return HttpAdapter.adapt(req, res, AuthService.LoginParams.class, (body) -> {
                var service = new AuthService();
                return service.login(body);
            });
        });
    }
}
