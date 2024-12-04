package com.app.catalogmanager.DTO.request;

import javax.validation.constraints.NotBlank;

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
   @NotBlank(message = "Name is required")
    private String name;
}