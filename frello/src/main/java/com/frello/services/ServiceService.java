package com.frello.services;

import com.frello.daos.service.SQLServiceDAO;
import com.frello.daos.service.ServiceDAO;
import com.frello.models.service.Service;
import com.frello.services.common.HttpException;

import java.util.UUID;

public class ServiceService {
    private static final ServiceDAO serviceDAO = new SQLServiceDAO();

    public static Service service(UUID id) throws HttpException {
        return serviceDAO
                .service(id)
                .orElseThrow(() -> new HttpException(404, "Not found"));
    }
}
