package com.app.catalogmanager.DTO.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitsRequest {

    private Long id;

    private String designation;

    private double prix;

    private int quantite;

    private CategoriesRequest categorie;
}
