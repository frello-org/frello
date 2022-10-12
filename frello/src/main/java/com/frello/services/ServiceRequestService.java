package com.frello.services;

import com.frello.daos.service.SQLServiceRequestDAO;
import com.frello.daos.service.ServiceRequestDAO;
import com.frello.models.service.ServiceRequest;

import java.util.List;

public class ServiceRequestService {
    private static ServiceRequestDAO serviceRequestDAO = new SQLServiceRequestDAO();

    public static List<ServiceRequest> serviceRequests() {
        return serviceRequestDAO.serviceRequests();
    }
}
