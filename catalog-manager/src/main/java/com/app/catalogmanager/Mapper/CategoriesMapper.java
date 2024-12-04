package com.app.catalogmanager.Mapper;

import org.mapstruct.Mapper;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;
import com.app.catalogmanager.Entity.Categories;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {
    Categories toEntity(CategoriesRequest categoriesRequest);

    CategoriesResponse toResponse(Categories categories);
}
