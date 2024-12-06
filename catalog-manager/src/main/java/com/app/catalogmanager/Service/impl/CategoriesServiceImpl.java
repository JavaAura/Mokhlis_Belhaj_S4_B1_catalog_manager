package com.app.catalogmanager.Service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;
import com.app.catalogmanager.Entity.Categories;
import com.app.catalogmanager.Mapper.CategoriesMapper;
import com.app.catalogmanager.Repository.CategoriesRepository;
import com.app.catalogmanager.Service.CategoriesService;
import com.app.catalogmanager.Exception.ResourceNotFoundException;
import com.app.catalogmanager.Validation.CategoriesValidation;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private CategoriesValidation categoriesValidation;

    @Override
    public CategoriesResponse createCategories(CategoriesRequest categoriesRequest) {
        categoriesValidation.validateCreateRequest(categoriesRequest);
        Categories categories = categoriesMapper.toEntity(categoriesRequest);
        categoriesRepository.save(categories);
        return categoriesMapper.toResponse(categories);
    }

    @Override
    public CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest) {
        categoriesValidation.validateUpdateRequest(id, categoriesRequest);
        Categories categories = categoriesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categories.setName(categoriesRequest.getName());
        categoriesRepository.save(categories);
        return categoriesMapper.toResponse(categories);
    }

    @Override
    public boolean deleteCategories(Long id) {
        categoriesValidation.validateDeleteRequest(id);
        Optional<Categories> categoryOptional = categoriesRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoriesRepository.delete(categoryOptional.get());
            return true;
        }
        return false;
    }


    @Override
    public Page<CategoriesResponse> allcategories(Pageable pageable) {
        Page<Categories> categories = categoriesRepository.findAll(pageable);
        return categories.map(categoriesMapper::toResponse);
    }

    @Override
    public Page<CategoriesResponse> getCategoriesByName(String name, Pageable pageable) {
        Page<Categories> categories = categoriesRepository.findByNameContainingIgnoreCase(name,pageable);
        if (categories == null) {
            throw new RuntimeException("Categories not found");
        }
        return categories.map(categoriesMapper::toResponse);
    }
}
