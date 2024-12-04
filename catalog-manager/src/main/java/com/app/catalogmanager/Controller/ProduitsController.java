package com.app.catalogmanager.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.DTO.response.ProduitsResponse;
import com.app.catalogmanager.Service.ProduitsService;

@RestController
@RequestMapping("api/produits")
public class ProduitsController {

    @Autowired
    private ProduitsService produitsService;

    @PostMapping
    public ResponseEntity<ProduitsResponse> createProduits(@Valid @RequestBody ProduitsRequest produitsRequest) {
        ProduitsResponse produitsResponse = produitsService.createProduits(produitsRequest);
        return ResponseEntity.ok(produitsResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitsResponse> updateProduits(@PathVariable Long id, @Valid @RequestBody ProduitsRequest produitsRequest) {
        ProduitsResponse produitsResponse = produitsService.updateProduits(id, produitsRequest);
        return ResponseEntity.ok(produitsResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduits(@PathVariable Long id) {
        boolean deleted = produitsService.deleteProduits(id);
        if (!deleted) {
            throw new RuntimeException("Produits not found");
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    
}
