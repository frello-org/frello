package com.frello.services;

import com.frello.daos.service.SQLServiceDAO;
import com.frello.daos.service.ServiceDAO;
import com.frello.models.service.Service;
import com.frello.models.user.User;
import com.frello.services.common.HttpException;

import java.util.List;
import java.util.UUID;

public class ServiceService {
    private static final ServiceDAO serviceDAO = new SQLServiceDAO();

    public static List<Service> services(User loggedUser) throws HttpException {
        if (!loggedUser.isAdmin()) {
            throw new HttpException(403, "Unauthorized: this is an admin-only resource");
        }
        return serviceDAO.services();
    }

    public static Service service(UUID id) throws HttpException {
        return serviceDAO
                .service(id)
                .orElseThrow(() -> new HttpException(404, "Not found"));
    }

    public static List<Service> myServices(User loggedUser, Mode mode) {
        var id = loggedUser.getId();
        return switch (mode) {
            case AS_CONSUMER -> serviceDAO.userConsumedServices(id);
            case AS_PROVIDER -> serviceDAO.userProvidedServices(id);
        };
    }

    public enum Mode {
        AS_CONSUMER,
        AS_PROVIDER;

        public static Mode fromString(String raw) throws HttpException {
            return switch (raw) {
                case "AS_CONSUMER" -> Mode.AS_CONSUMER;
                case "AS_PROVIDER" -> Mode.AS_PROVIDER;
                default -> {
                    var message = String.format(
                            "Invalid mode (`%s`), expecting `AS_CONSUMER` or `AS_PROVIDER`", raw);
                    throw new HttpException(400, message);
                }
            };
        }
    }
}
