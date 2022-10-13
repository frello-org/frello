package com.frello.daos.service;

import com.frello.models.service.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceDAO {
    Optional<Service> service(UUID id);

    List<Service> userConsumedServices(UUID consumerId);
    
    List<Service> userProvidedServices(UUID providerId);
}
