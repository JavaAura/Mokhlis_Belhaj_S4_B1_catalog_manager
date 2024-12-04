package com.app.catalogmanager.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public ProduitsResponse updateProduits(Long id, ProduitsRequest produitsRequest) {
        Produits produits = produitsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Produits not found"));
        if(produitsRequest.getDesignation() != null) {
            produits.setDesignation(produitsRequest.getDesignation());
        }
        if(produitsRequest.getPrix() != null) {
            Double prix = produitsRequest.getPrix();
            if (prix <= 0) {
                throw new IllegalArgumentException("Prix must be greater than 0");
            }
            produits.setPrix(prix);
        }
        if(produitsRequest.getQuantite() != null) {
            if (produitsRequest.getQuantite() < 0) {
                throw new IllegalArgumentException("Quantite cannot be negative");
            }
            produits.setQuantite(produitsRequest.getQuantite());
        }
        if(produitsRequest.getCategorie() != null) {
            Categories categories = categoriesRepository.findById(produitsRequest.getCategorie().getId())
            .orElseThrow(() -> new RuntimeException("Categorie not found"));
            produits.setCategorie(categories);
        }
        produitsRepository.save(produits);
        return produitsMapper.toResponse(produits);
    }

    @Override
    public boolean deleteProduits(Long id) {
        boolean exists = produitsRepository.existsById(id);
        if (!exists) {
            return false;
        }else{
            produitsRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Page<ProduitsResponse> getAllProduits(Pageable pageable) {
        Page<Produits> produits = produitsRepository.findAll(pageable);
        return produits.map(produitsMapper::toResponse);
    }
}
