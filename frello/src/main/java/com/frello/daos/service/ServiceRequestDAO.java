package com.frello.daos.service;

import com.frello.models.service.ServiceRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRequestDAO {
    Optional<ServiceRequest> serviceRequest(UUID id);

    List<ServiceRequest> serviceRequests();
}
