package com.app.catalogmanager.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProduitsResponse {
    private String designation;
    private double prix;
    private int quantite;
    private String categorieName;
}
