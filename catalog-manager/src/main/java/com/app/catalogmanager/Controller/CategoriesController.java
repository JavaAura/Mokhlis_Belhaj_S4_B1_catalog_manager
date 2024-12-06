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
import com.app.catalogmanager.Exception.BadRequestException;
import com.app.catalogmanager.Exception.ResourceNotFoundException;


@RestController
@RequestMapping("/api")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    @PostMapping("/admin/categories")
    public ResponseEntity<CategoriesResponse> createCategories(@Valid @RequestBody CategoriesRequest categoriesRequest) {
        CategoriesResponse response = categoriesService.createCategories(categoriesRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoriesResponse> updateCategories(@PathVariable Long id, @Valid @RequestBody CategoriesRequest categoriesRequest) {
        CategoriesResponse response = categoriesService.updateCategories(id, categoriesRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<Boolean> deleteCategories(@PathVariable Long id) {
        boolean deleted = categoriesService.deleteCategories(id);
        if(!deleted) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
                }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/categories")
    public ResponseEntity<Page<CategoriesResponse>> allcategories(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CategoriesResponse> response = categoriesService.allcategories(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/categories/name")
    public ResponseEntity<Page<CategoriesResponse>> getCategoriesByName(String name, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Name cannot be null or empty");
        }
        Page<CategoriesResponse> response = categoriesService.getCategoriesByName(name, pageable);
        return ResponseEntity.ok(response);
    }
    
}
