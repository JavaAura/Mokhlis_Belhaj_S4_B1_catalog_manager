package com.app.catalogmanager.DTO.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data   
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesResponse {
    private Long id;
    private String name;
}
