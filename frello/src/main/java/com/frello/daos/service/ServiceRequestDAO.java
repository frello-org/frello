package com.frello.daos.service;

import com.frello.lib.exceptions.NotFoundException;
import com.frello.models.service.ServiceRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRequestDAO {
    Optional<ServiceRequest> serviceRequest(UUID id);

    List<ServiceRequest> serviceRequests();

    void createIgnoringCategories(ServiceRequest serviceRequest);

    void createWithCategories(ServiceRequest serviceRequest, List<UUID> categoryIDs) throws NotFoundException;
}
