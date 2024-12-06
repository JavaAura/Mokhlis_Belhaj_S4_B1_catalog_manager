package com.app.catalogmanager.Validation;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.Exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CategoriesValidation {
    
    public void validateCreateRequest(CategoriesRequest categoriesRequest) {
        validateName(categoriesRequest.getName());
        validateDescription(categoriesRequest.getDescription());
    }

    public void validateUpdateRequest(Long id, CategoriesRequest categoriesRequest) {
        if (id == null) {
            throw new BadRequestException("Id cannot be null");
        }
        if (categoriesRequest == null) {
            throw new BadRequestException("Categories request cannot be null");
        }
        validateName(categoriesRequest.getName());
    }

    public void validateDeleteRequest(Long id) {
        if (id == null) {
            throw new BadRequestException("Id cannot be null");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Category name cannot be null or empty");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new BadRequestException("Category name must be between 2 and 50 characters");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new BadRequestException("Category description cannot be null or empty");
            }
        }
    }

