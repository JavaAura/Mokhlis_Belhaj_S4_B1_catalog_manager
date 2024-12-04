package com.app.catalogmanager.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.DTO.response.ProduitsResponse;
import com.app.catalogmanager.Entity.Produits;

@Mapper(componentModel = "spring")
public interface ProduitsMapper  {

    @Mapping(source = "categorie.name", target = "categorieName")
    ProduitsResponse toResponse(Produits produits);


    Produits toEntity(ProduitsRequest produitsRequest);
}
