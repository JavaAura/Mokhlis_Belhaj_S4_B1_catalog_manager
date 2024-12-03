package com.app.catalogmanager.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;
import com.app.catalogmanager.Service.CategoriesService;


@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    @PostMapping
    public ResponseEntity<CategoriesResponse> createCategories(@Valid @RequestBody CategoriesRequest categoriesRequest) {
        CategoriesResponse response = categoriesService.createCategories(categoriesRequest);
        return ResponseEntity.ok(response);
    }
    
}
