package com.app.catalogmanager.Service;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;

public interface CategoriesService {
    CategoriesResponse createCategories(CategoriesRequest categoriesRequest);
    CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest);
    boolean deleteCategories(Long id);
}

