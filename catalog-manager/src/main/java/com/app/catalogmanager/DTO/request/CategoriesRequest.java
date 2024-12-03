package com.app.catalogmanager.DTO.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class CategoriesRequest {

    private Long id;
//    @NotBlank(message = "Name is required")
    private String name;
}