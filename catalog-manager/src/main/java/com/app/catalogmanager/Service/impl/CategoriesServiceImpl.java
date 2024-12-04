package com.app.catalogmanager.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;
import com.app.catalogmanager.Entity.Categories;
import com.app.catalogmanager.Mapper.CategoriesMapper;
import com.app.catalogmanager.Repository.CategoriesRepository;
import com.app.catalogmanager.Service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public CategoriesResponse createCategories(CategoriesRequest categoriesRequest) {
        Categories categories = categoriesMapper.toEntity(categoriesRequest);
        categoriesRepository.save(categories);
        return categoriesMapper.toResponse(categories);
    }

    @Override
    public CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest) {
        Categories categories = categoriesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categories not found"));
        categories.setName(categoriesRequest.getName());
        categoriesRepository.save(categories);
        return categoriesMapper.toResponse(categories);
    }

    @Override
    public boolean deleteCategories(Long id) {
        categoriesRepository.deleteById(id);
        return true;
    }
}
