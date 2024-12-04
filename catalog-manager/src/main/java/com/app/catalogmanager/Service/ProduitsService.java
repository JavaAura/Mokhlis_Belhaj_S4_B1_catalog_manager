package com.app.catalogmanager.Service;


import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.DTO.response.ProduitsResponse;

public interface ProduitsService {

    ProduitsResponse createProduits(ProduitsRequest produitsRequest);
    
}
