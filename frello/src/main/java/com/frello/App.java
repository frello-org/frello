package com.frello;

import static spark.Spark.*;

import com.frello.services.AuthService;
import com.frello.services.ServiceCategoryService;
import com.frello.services.UserService;
import com.frello.services.common.HttpAdapter;

public class App {
    public static void main(String[] args) {
        port(3333);

        serviceRoutes();
        serviceCategoryRoutes();
        serviceRequestRoutes();
        authRoutes();
    }

    private static void serviceRoutes() {
        path("/services", () -> {
            get("/my-services", (req, res) -> {
                var mode = req.queryParams("mode");
                return null;
            });

            get("/:id", (req, res) -> {
                return null;
            });
        });
    }

    private static void serviceCategoryRoutes() {
        path("/service-categories", () -> {
            get("/", (req, res) -> new HttpAdapter(req, res)
                    .adapt(ServiceCategoryService::categories));
        });
    }

    private static void serviceRequestRoutes() {
        path("/service-requests", () -> {
            get("/", (req, res) -> {
                return null;
            });

            get("/:id", (req, res) -> {
                return null;
            });

            post("/", (req, res) -> {
                return null;
            });

            post("/:id/apply-as-provider", (req, res) -> {
                return null;
            });
        });
    }

    private static void authRoutes() {
        path("/auth", () -> {
            post("/login", (req, res) -> {
                var adapter = new HttpAdapter(req, res);
                return adapter.adapt(AuthService.LoginParams.class, AuthService::login);
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
                    return adapter.guard(UserService::me);
                });
            });
        });
    }
}
