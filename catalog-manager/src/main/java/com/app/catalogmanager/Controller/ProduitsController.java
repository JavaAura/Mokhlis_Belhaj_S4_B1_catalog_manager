package com.app.catalogmanager.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
