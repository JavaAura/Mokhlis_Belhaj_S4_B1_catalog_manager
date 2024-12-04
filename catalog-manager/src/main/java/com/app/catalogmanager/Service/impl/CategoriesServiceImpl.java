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

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public CategoriesResponse createCategories(CategoriesRequest categoriesRequest) {
        if(categoriesRequest == null) {
            throw new IllegalArgumentException("Categories request cannot be null");
        }
        Categories categories = categoriesMapper.toEntity(categoriesRequest);
        categoriesRepository.save(categories);
        return categoriesMapper.toResponse(categories);
    }

    @Override
    public CategoriesResponse updateCategories(Long id, CategoriesRequest categoriesRequest) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Categories categories = categoriesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categories not found"));
        categories.setName(categoriesRequest.getName());
        categoriesRepository.save(categories);
        return categoriesMapper.toResponse(categories);
    }

    @Override
    public boolean deleteCategories(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Categories> categories = categoriesRepository.findById(id);

        if (!categories.isPresent()) {
            return false;
        }else{
            categoriesRepository.delete(categories.get());
            return true;
        }
    }


    @Override
    public Page<CategoriesResponse> allcategories(Pageable pageable) {
        Page<Categories> categories = categoriesRepository.findAll(pageable);
        return categories.map(categoriesMapper::toResponse);
    }
}
