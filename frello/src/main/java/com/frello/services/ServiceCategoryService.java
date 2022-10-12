package com.frello.services;

import com.frello.daos.service.SQLServiceCategoryDAO;
import com.frello.daos.service.ServiceCategoryDAO;
import com.frello.models.service.ServiceCategory;

import java.util.List;

public class ServiceCategoryService {
    private static ServiceCategoryDAO serviceCategoryDAO = new SQLServiceCategoryDAO();

    public static List<ServiceCategory> categories() {
        return serviceCategoryDAO.categories();
    }
}
