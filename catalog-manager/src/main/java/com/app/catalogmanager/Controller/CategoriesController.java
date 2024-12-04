package com.app.catalogmanager.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategories(@PathVariable Long id) {
        boolean deleted = categoriesService.deleteCategories(id);
        if(deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<CategoriesResponse>> allcategories(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CategoriesResponse> response = categoriesService.allcategories(pageable);
        return ResponseEntity.ok(response);
    }
    
}
