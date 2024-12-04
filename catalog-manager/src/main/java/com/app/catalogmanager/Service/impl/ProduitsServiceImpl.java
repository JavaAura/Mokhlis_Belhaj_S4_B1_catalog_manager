package com.app.catalogmanager.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.DTO.response.ProduitsResponse;
import com.app.catalogmanager.Entity.Produits;
import com.app.catalogmanager.Entity.Categories;
import com.app.catalogmanager.Mapper.ProduitsMapper;
import com.app.catalogmanager.Repository.ProduitsRepository;
import com.app.catalogmanager.Repository.CategoriesRepository;
import com.app.catalogmanager.Service.ProduitsService;


@Service
public class ProduitsServiceImpl implements ProduitsService {

    @Autowired
    private ProduitsRepository produitsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ProduitsMapper produitsMapper;

    @Override
    public ProduitsResponse createProduits(ProduitsRequest produitsRequest) {
        Produits produits = produitsMapper.toEntity(produitsRequest);
        Categories categories = categoriesRepository.findById(produitsRequest.getCategorie().getId())
        .orElseThrow(() -> new RuntimeException("Categorie not found"));
        produits.setCategorie(categories);
        produitsRepository.save(produits);
        return produitsMapper.toResponse(produits);
    }
}
