package com.app.catalogmanager.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;

public interface CategoriesService {
    CategoriesResponse createCategories(CategoriesRequest categoriesRequest);
    CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest);
    boolean deleteCategories(Long id);
    Page<CategoriesResponse> allcategories(Pageable pageable);
}


