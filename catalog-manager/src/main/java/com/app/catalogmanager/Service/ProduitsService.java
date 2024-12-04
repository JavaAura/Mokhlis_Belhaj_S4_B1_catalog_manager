package com.app.catalogmanager.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.DTO.response.ProduitsResponse;

public interface ProduitsService {

    ProduitsResponse createProduits(ProduitsRequest produitsRequest);
    ProduitsResponse updateProduits(Long id, ProduitsRequest produitsRequest);
    boolean deleteProduits(Long id);
    Page<ProduitsResponse> getAllProduits(Pageable pageable);
    
}
