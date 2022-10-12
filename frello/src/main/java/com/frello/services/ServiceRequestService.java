package com.frello.services;

import com.frello.daos.service.SQLServiceRequestDAO;
import com.frello.daos.service.ServiceRequestDAO;
import com.frello.models.service.ServiceRequest;
import com.frello.services.common.HttpException;

import java.util.List;
import java.util.UUID;

public class ServiceRequestService {
    private static ServiceRequestDAO serviceRequestDAO = new SQLServiceRequestDAO();

    public static ServiceRequest serviceRequest(UUID id) throws HttpException {
        return serviceRequestDAO
                .serviceRequest(id)
                .orElseThrow(() -> new HttpException(404, "Not found"));
    }

    public static List<ServiceRequest> serviceRequests() {
        return serviceRequestDAO.serviceRequests();
    }
}
