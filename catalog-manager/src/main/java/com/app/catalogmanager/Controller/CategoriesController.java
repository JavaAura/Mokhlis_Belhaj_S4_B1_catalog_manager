package com.app.catalogmanager.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesResponse> updateCategories(@PathVariable Long id, @Valid @RequestBody CategoriesRequest categoriesRequest) {
        CategoriesResponse response = categoriesService.updateCategories(id, categoriesRequest);
        return ResponseEntity.ok(response);
    }
    
}
