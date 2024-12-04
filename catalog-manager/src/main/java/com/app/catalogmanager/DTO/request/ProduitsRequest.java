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

    private Double prix;

    private Integer quantite;

    private CategoriesRequest categorie;
}
