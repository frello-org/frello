package com.frello;

import static spark.Spark.*;

import com.frello.services.*;
import com.frello.services.common.External;
import com.frello.services.common.HttpAdapter;
import com.frello.services.common.HttpException;

public class App {
    public static void main(String[] args) {
        port(3333);

        notFound((req, res) -> {
            res.header("Content-Type", "application/json");
            res.status(404);
            return HttpAdapter.makeJSON(new HttpAdapter.UserError("Not found (endpoint)"));
        });

        internalServerError((req, res) -> {
            res.header("Content-Type", "application/json");
            res.status(500);
            return HttpAdapter.makeJSON(new HttpAdapter.UserError("Internal server error"));
        });

        serviceRoutes();
        serviceCategoryRoutes();
        serviceRequestRoutes();
        authRoutes();
    }

    private static void serviceRoutes() {
        path("/services", () -> {
            get("/", (req, res) -> new HttpAdapter(req, res)
                    .adaptWithGuard(ServiceService::services));

            get("/my-services", (req, res) -> new HttpAdapter(req, res)
                    .adaptWithGuard((user) -> {
                        var rawMode = req.queryParams("mode");
                        if (rawMode == null) {
                            throw new HttpException(400, "Missing `mode` query parameter");
                        }
                        var mode = ServiceService.Mode.fromString(rawMode);
                        return ServiceService.myServices(user, mode);
                    }));

            get("/:id", (req, res) -> new HttpAdapter(req, res)
                    .adaptWithGuard((_user) -> {
                        var id = External.uuid(req.params(":id"));
                        return ServiceService.service(id);
                    }));
        });
    }

    private static void serviceCategoryRoutes() {
        path("/service-categories", () -> {
            get("/", (req, res) -> new HttpAdapter(req, res)
                    .adaptWithGuard((_user) -> ServiceCategoryService.categories()));
        });
    }

    private static void serviceRequestRoutes() {
        path("/service-requests", () -> {
            get("/", (req, res) -> new HttpAdapter(req, res)
                    .adaptWithGuard((_user) -> ServiceRequestService.serviceRequests()));

            get("/:id", (req, res) -> new HttpAdapter(req, res)
                    .adaptWithGuard((_user) -> {
                        var id = External.uuid(req.params(":id"));
                        return ServiceRequestService.serviceRequest(id);
                    }));

            post("/", (req, res) -> {
                var adapter = new HttpAdapter(req, res);
                return adapter.adapt(ServiceRequestService.CreateServiceRequestParams.class, (body) -> {
                    return adapter.guard((user) -> {
                        var resp = ServiceRequestService.createService(body, user);
                        res.status(201);
                        return resp;
                    });
                });
            });

            post("/:id/apply-as-provider", (req, res) -> {
                return new HttpAdapter(req, res).adaptWithGuard((user) -> {
                    var id = External.uuid(req.params(":id"));
                    var resp = ServiceRequestService.applyAsProvider(id, user);
                    res.status(201);
                    return resp;
                });
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
