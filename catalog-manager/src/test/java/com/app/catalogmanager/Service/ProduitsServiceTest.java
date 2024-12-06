package com.app.catalogmanager.Service;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.request.ProduitsRequest;
import com.app.catalogmanager.DTO.response.ProduitsResponse;
import com.app.catalogmanager.Entity.Categories;
import com.app.catalogmanager.Entity.Produits;
import com.app.catalogmanager.Exception.BadRequestException;
import com.app.catalogmanager.Exception.ResourceNotFoundException;
import com.app.catalogmanager.Mapper.ProduitsMapper;
import com.app.catalogmanager.Repository.CategoriesRepository;
import com.app.catalogmanager.Repository.ProduitsRepository;
import com.app.catalogmanager.Service.impl.ProduitsServiceImpl;
import com.app.catalogmanager.Validation.ProduitsValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitsServiceTest {

    @Mock
    private ProduitsRepository produitsRepository;

    @Mock
    private CategoriesRepository categoriesRepository;

    @Mock
    private ProduitsMapper produitsMapper;

    @Mock
    private ProduitsValidation produitsValidation;

    @InjectMocks
    private ProduitsServiceImpl produitsService;

    private ProduitsRequest produitsRequest;
    private Produits produit;
    private ProduitsResponse produitsResponse;
    private Categories category;
    private CategoriesRequest categoriesRequest;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        category = Categories.builder()
                .id(1L)
                .name("Test Category")
                .description("Test Description")
                .build();

        categoriesRequest = CategoriesRequest.builder()
                .id(1L)
                .name("Test Category")
                .description("Test Description")
                .build();

        produitsRequest = ProduitsRequest.builder()
                .designation("Test Product")
                .prix(100.0)
                .quantite(10)
                .categorie(categoriesRequest)
                .build();

        produit = Produits.builder()
                .id(1L)
                .designation("Test Product")
                .prix(100.0)
                .quantite(10)
                .categorie(category)
                .build();

        produitsResponse = ProduitsResponse.builder()
                .designation("Test Product")
                .prix(100.0)
                .quantite(10)
                .categorieName("Test Category")
                .build();

        pageable = PageRequest.of(0, 10);
    }

    @Nested
    @DisplayName("Create Produits Tests")
    class CreateProduitsTests {

        @Test
        @DisplayName("Should successfully create a product")
        void createProduits_Success() {
            // Arrange
            doNothing().when(produitsValidation).validateCreateRequest(any(ProduitsRequest.class));
            when(categoriesRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
            when(produitsMapper.toEntity(any(ProduitsRequest.class))).thenReturn(produit);
            when(produitsRepository.save(any(Produits.class))).thenReturn(produit);
            when(produitsMapper.toResponse(any(Produits.class))).thenReturn(produitsResponse);

            // Act
            ProduitsResponse result = produitsService.createProduits(produitsRequest);

            // Assert
            assertNotNull(result);
            assertEquals(produitsResponse.getDesignation(), result.getDesignation());
            assertEquals(produitsResponse.getPrix(), result.getPrix());
            assertEquals(produitsResponse.getQuantite(), result.getQuantite());
            assertEquals(produitsResponse.getCategorieName(), result.getCategorieName());
            
            verify(produitsValidation).validateCreateRequest(produitsRequest);
            verify(categoriesRepository).findById(categoriesRequest.getId());
            verify(produitsMapper).toEntity(produitsRequest);
            verify(produitsRepository).save(produit);
            verify(produitsMapper).toResponse(produit);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when category not found")
        void createProduits_CategoryNotFound() {
            // Arrange
            when(categoriesRepository.findById(any(Long.class))).thenReturn(Optional.empty());
            doNothing().when(produitsValidation).validateCreateRequest(any(ProduitsRequest.class));

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, 
                () -> produitsService.createProduits(produitsRequest)
            );
            
            verify(produitsRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Update Produits Tests")
    class UpdateProduitsTests {

        @Test
        @DisplayName("Should successfully update a product")
        void updateProduits_Success() {
            // Arrange
            Long id = 1L;
            doNothing().when(produitsValidation).validateUpdateRequest(id, produitsRequest);
            when(produitsRepository.findById(id)).thenReturn(Optional.of(produit));
            when(categoriesRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
            when(produitsRepository.save(any(Produits.class))).thenReturn(produit);
            when(produitsMapper.toResponse(any(Produits.class))).thenReturn(produitsResponse);

            // Act
            ProduitsResponse result = produitsService.updateProduits(id, produitsRequest);

            // Assert
            assertNotNull(result);
            assertEquals(produitsResponse.getDesignation(), result.getDesignation());
            verify(produitsValidation).validateUpdateRequest(id, produitsRequest);
            verify(produitsRepository).findById(id);
            verify(produitsRepository).save(any(Produits.class));
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when product not found")
        void updateProduits_ProductNotFound() {
            // Arrange
            Long id = 1L;
            when(produitsRepository.findById(id)).thenReturn(Optional.empty());
            doNothing().when(produitsValidation).validateUpdateRequest(id, produitsRequest);

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, 
                () -> produitsService.updateProduits(id, produitsRequest)
            );
            
            verify(produitsRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Delete Produits Tests")
    class DeleteProduitsTests {

        @Test
        @DisplayName("Should successfully delete a product")
        void deleteProduits_Success() {
            // Arrange
            Long id = 1L;
            doNothing().when(produitsValidation).validateDeleteRequest(id);
            when(produitsRepository.existsById(id)).thenReturn(true);
            doNothing().when(produitsRepository).deleteById(id);

            // Act
            boolean result = produitsService.deleteProduits(id);

            // Assert
            assertTrue(result);
            verify(produitsValidation).validateDeleteRequest(id);
            verify(produitsRepository).existsById(id);
            verify(produitsRepository).deleteById(id);
        }

        @Test
        @DisplayName("Should return false when product not found")
        void deleteProduits_ProductNotFound() {
            // Arrange
            Long id = 1L;
            doNothing().when(produitsValidation).validateDeleteRequest(id);
            when(produitsRepository.existsById(id)).thenReturn(false);

            // Act
            boolean result = produitsService.deleteProduits(id);

            // Assert
            assertFalse(result);
            verify(produitsRepository).existsById(id);
            verify(produitsRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Get Produits Tests")
    class GetProduitsTests {

        @Test
        @DisplayName("Should return all products with pagination")
        void getAllProduits_Success() {
            // Arrange
            List<Produits> produits = Arrays.asList(produit);
            Page<Produits> produitPage = new PageImpl<>(produits, pageable, produits.size());
            when(produitsRepository.findAll(pageable)).thenReturn(produitPage);
            when(produitsMapper.toResponse(any(Produits.class))).thenReturn(produitsResponse);

            // Act
            Page<ProduitsResponse> result = produitsService.getAllProduits(pageable);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(produitsResponse.getDesignation(), result.getContent().get(0).getDesignation());
            verify(produitsRepository).findAll(pageable);
            verify(produitsMapper, times(produits.size())).toResponse(any());
        }

        @Test
        @DisplayName("Should return products filtered by designation")
        void getProduitsByDesignation_Success() {
            // Arrange
            String designation = "Test";
            List<Produits> produits = Arrays.asList(produit);
            Page<Produits> produitPage = new PageImpl<>(produits, pageable, produits.size());
            when(produitsRepository.findByDesignationContainingIgnoreCase(designation, pageable))
                .thenReturn(produitPage);
            when(produitsMapper.toResponse(any(Produits.class))).thenReturn(produitsResponse);

            // Act
            Page<ProduitsResponse> result = produitsService.getProduitsByDesignation(designation, pageable);

            // Assert
            assertNotNull(result);
            assertFalse(result.getContent().isEmpty());
            assertEquals(produitsResponse.getDesignation(), result.getContent().get(0).getDesignation());
            verify(produitsRepository).findByDesignationContainingIgnoreCase(designation, pageable);
        }

        @Test
        @DisplayName("Should return products filtered by category")
        void getProduitsByCategorie_Success() {
            // Arrange
            Long categorieId = 1L;
            List<Produits> produits = Arrays.asList(produit);
            Page<Produits> produitPage = new PageImpl<>(produits, pageable, produits.size());
            when(produitsRepository.findByCategorieId(categorieId, pageable))
                .thenReturn(produitPage);
            when(produitsMapper.toResponse(any(Produits.class))).thenReturn(produitsResponse);

            // Act
            Page<ProduitsResponse> result = produitsService.getProduitsByCategorie(categorieId, pageable);

            // Assert
            assertNotNull(result);
            assertFalse(result.getContent().isEmpty());
            assertEquals(produitsResponse.getDesignation(), result.getContent().get(0).getDesignation());
            verify(produitsRepository).findByCategorieId(categorieId, pageable);
        }
    }
} 