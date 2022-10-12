package com.frello.daos.service;

import com.frello.models.service.ServiceCategory;

import java.util.List;
import java.util.UUID;

public interface ServiceCategoryDAO {
    List<ServiceCategory> categories();

    List<ServiceCategory> categoriesForRequest(UUID serviceRequestId);
}
