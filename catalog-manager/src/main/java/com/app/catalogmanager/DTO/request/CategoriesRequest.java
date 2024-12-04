package com.app.catalogmanager.DTO.request;


import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesRequest {

    private Long id;
    private String name;

    private String description;
}