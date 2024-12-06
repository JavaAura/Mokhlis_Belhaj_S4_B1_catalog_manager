package com.app.catalogmanager.Validation;

import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.Exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ProduitsValidation {

    public void validateCreateRequest(ProduitsRequest produitsRequest) {
        validateDesignation(produitsRequest.getDesignation());
        validatePrix(produitsRequest.getPrix());
        validateQuantite(produitsRequest.getQuantite());
        validateCategorie(produitsRequest.getCategorie().getId());
    }

    public void validateUpdateRequest(Long id, ProduitsRequest produitsRequest) {
        if (id == null) {
            throw new BadRequestException("Id cannot be null");
        }
        if (produitsRequest == null) {
            throw new BadRequestException("Produits request cannot be null");
        }
        validateDesignation(produitsRequest.getDesignation());
        validatePrix(produitsRequest.getPrix());
        validateQuantite(produitsRequest.getQuantite());
        validateCategorie(produitsRequest.getCategorie().getId());
    }

    public void validateDeleteRequest(Long id) {
        if (id == null) {
            throw new BadRequestException("Id cannot be null");
        }
    }

    private void validateDesignation(String designation) {
        if (designation == null || designation.trim().isEmpty()) {
            throw new BadRequestException("Designation cannot be null or empty");
        }
        if (designation.length() < 2 || designation.length() > 100) {
            throw new BadRequestException("Designation must be between 2 and 100 characters");
        }
    }

    private void validatePrix(Double prix) {
        if (prix == null || prix <= 0) {
            throw new BadRequestException("Prix must be greater than 0");
        }
    }

    private void validateQuantite(Integer quantite) {
        if (quantite == null || quantite < 0) {
            throw new BadRequestException("Quantite cannot be negative");
        }
    }

    private void validateCategorie(Long categorieId) {
        if (categorieId == null) {
            throw new BadRequestException("Categorie ID cannot be null");
        }
    }
} 