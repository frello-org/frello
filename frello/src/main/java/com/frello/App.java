package com.frello;

import static spark.Spark.*;

import com.frello.services.AuthService;
import com.frello.services.UserService;
import com.frello.services.common.HttpAdapter;

public class App {
    public static void main(String[] args) {
        port(3333);

        path("/auth", () -> {
            post("/login", (req, res) -> {
                var adapter = new HttpAdapter(req, res);
                return adapter.adapt(AuthService.LoginParams.class, (body) -> {
                    return AuthService.login(body);
                });
            });

            post("/register", (req, res) -> {
                var adapter = new HttpAdapter(req, res);
                return adapter.adapt(AuthService.RegisterParams.class, (body) -> {
                    AuthService.register(body);
                    res.status(201);
                    return null;
                });
            });

            get("/me", (req, res) -> {
                var adapter = new HttpAdapter(req, res);
                return adapter.adapt(() -> {
                    return adapter.guard((user) -> {
                        return UserService.me(user);
                    });
                });
            });
        });
    }
}
