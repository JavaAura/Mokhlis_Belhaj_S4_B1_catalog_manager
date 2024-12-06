package com.app.catalogmanager.Service;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;
import com.app.catalogmanager.Entity.Categories;
import com.app.catalogmanager.Exception.BadRequestException;
import com.app.catalogmanager.Exception.ResourceNotFoundException;
import com.app.catalogmanager.Mapper.CategoriesMapper;
import com.app.catalogmanager.Repository.CategoriesRepository;
import com.app.catalogmanager.Service.impl.CategoriesServiceImpl;
import com.app.catalogmanager.Validation.CategoriesValidation;
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
class CategoriesServiceTest {

    @Mock
    private CategoriesRepository categoriesRepository;

    @Mock
    private CategoriesMapper categoriesMapper;

    @Mock
    private CategoriesValidation categoriesValidation;

    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    private CategoriesRequest categoriesRequest;
    private Categories category;
    private CategoriesResponse categoriesResponse;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        categoriesRequest = CategoriesRequest.builder()
                .name("Test Category")
                .description("Description Test")
                .build();

        category = Categories.builder()
                .id(1L)
                .name("Test Category")
                .description("Description Test")
                .build();

        categoriesResponse = CategoriesResponse.builder()
                .name("Test Category")
                .description("Description Test")
                .build();

        pageable = PageRequest.of(0, 10);
    }

    @Nested
    @DisplayName("Create Categories Tests")
    class CreateCategoriesTests {

        @Test
        @DisplayName("Should successfully create a category")
        void createCategories_Success() {
            // Arrange
            doNothing().when(categoriesValidation).validateCreateRequest(any(CategoriesRequest.class));
            when(categoriesMapper.toEntity(any(CategoriesRequest.class))).thenReturn(category);
            when(categoriesRepository.save(any(Categories.class))).thenReturn(category);
            when(categoriesMapper.toResponse(any(Categories.class))).thenReturn(categoriesResponse);

            // Act
            CategoriesResponse result = categoriesService.createCategories(categoriesRequest);

            // Assert
            assertNotNull(result);
            assertEquals(categoriesResponse.getName(), result.getName());
            assertEquals(categoriesResponse.getDescription(), result.getDescription());
            verify(categoriesValidation).validateCreateRequest(categoriesRequest);
            verify(categoriesMapper).toEntity(categoriesRequest);
            verify(categoriesRepository).save(category);
            verify(categoriesMapper).toResponse(category);
        }

        @Test
        @DisplayName("Should throw BadRequestException when validation fails")
        void createCategories_ValidationFails() {
            // Arrange
            String errorMessage = "Invalid category data";
            doThrow(new BadRequestException(errorMessage))
                .when(categoriesValidation).validateCreateRequest(any(CategoriesRequest.class));

            // Act & Assert
            BadRequestException exception = assertThrows(BadRequestException.class, 
                () -> categoriesService.createCategories(categoriesRequest)
            );
            assertEquals(errorMessage, exception.getMessage());
            verify(categoriesRepository, never()).save(any());
            verify(categoriesMapper, never()).toEntity(any());
            verify(categoriesMapper, never()).toResponse(any());
        }

      
    }

    @Nested
    @DisplayName("Update Categories Tests")
    class UpdateCategoriesTests {

        @Test
        @DisplayName("Should successfully update a category")
        void updateCategories_Success() {
            // Arrange
            Long id = 1L;
            doNothing().when(categoriesValidation).validateUpdateRequest(id, categoriesRequest);
            when(categoriesRepository.findById(id)).thenReturn(Optional.of(category));
            when(categoriesRepository.save(any(Categories.class))).thenReturn(category);
            when(categoriesMapper.toResponse(any(Categories.class))).thenReturn(categoriesResponse);

            // Act
            CategoriesResponse result = categoriesService.updateCategories(id, categoriesRequest);

            // Assert
            assertNotNull(result);
            assertEquals(categoriesResponse.getName(), result.getName());
            assertEquals(categoriesResponse.getDescription(), result.getDescription());
            verify(categoriesValidation).validateUpdateRequest(id, categoriesRequest);
            verify(categoriesRepository).findById(id);
            verify(categoriesRepository).save(category);
            verify(categoriesMapper).toResponse(category);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when category not found")
        void updateCategories_CategoryNotFound() {
            // Arrange
            Long id = 1L;
            doNothing().when(categoriesValidation).validateUpdateRequest(id, categoriesRequest);
            when(categoriesRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> categoriesService.updateCategories(id, categoriesRequest)
            );
            assertEquals("Category not found with id: " + id, exception.getMessage());
            verify(categoriesRepository).findById(id);
            verify(categoriesRepository, never()).save(any());
            verify(categoriesMapper, never()).toResponse(any());
        }
    }

    @Nested
    @DisplayName("Delete Categories Tests")
    class DeleteCategoriesTests {

        @Test
        @DisplayName("Should successfully delete a category")
        void deleteCategories_Success() {
            // Arrange
            Long id = 1L;
            doNothing().when(categoriesValidation).validateDeleteRequest(id);
            when(categoriesRepository.findById(id)).thenReturn(Optional.of(category));
            doNothing().when(categoriesRepository).delete(any(Categories.class));

            // Act
            boolean result = categoriesService.deleteCategories(id);

            // Assert
            assertTrue(result);
            verify(categoriesValidation).validateDeleteRequest(id);
            verify(categoriesRepository).findById(id);
            verify(categoriesRepository).delete(category);
        }

        @Test
        @DisplayName("Should return false when category not found")
        void deleteCategories_CategoryNotFound() {
            // Arrange
            Long id = 1L;
            doNothing().when(categoriesValidation).validateDeleteRequest(id);
            when(categoriesRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            boolean result = categoriesService.deleteCategories(id);

            // Assert
            assertFalse(result);
            verify(categoriesValidation).validateDeleteRequest(id);
            verify(categoriesRepository).findById(id);
            verify(categoriesRepository, never()).delete(any());
        }
    }

    @Nested
    @DisplayName("Get Categories Tests")
    class GetCategoriesTests {

        @Test
        @DisplayName("Should return all categories with pagination")
        void getAllCategories_Success() {
            // Arrange
            List<Categories> categories = Arrays.asList(category);
            Page<Categories> categoryPage = new PageImpl<>(categories, pageable, categories.size());
            when(categoriesRepository.findAll(pageable)).thenReturn(categoryPage);
            when(categoriesMapper.toResponse(any(Categories.class))).thenReturn(categoriesResponse);

            // Act
            Page<CategoriesResponse> result = categoriesService.allcategories(pageable);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(categoriesResponse.getName(), result.getContent().get(0).getName());
            verify(categoriesRepository).findAll(pageable);
            verify(categoriesMapper, times(categories.size())).toResponse(any(Categories.class));
        }

        @Test
        @DisplayName("Should return empty page when no categories exist")
        void getAllCategories_EmptyResult() {
            // Arrange
            Page<Categories> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
            when(categoriesRepository.findAll(pageable)).thenReturn(emptyPage);

            // Act
            Page<CategoriesResponse> result = categoriesService.allcategories(pageable);

            // Assert
            assertNotNull(result);
            assertTrue(result.getContent().isEmpty());
            assertEquals(0, result.getTotalElements());
            verify(categoriesRepository).findAll(pageable);
            verify(categoriesMapper, never()).toResponse(any());
        }

        @Test
        @DisplayName("Should return filtered categories by name")
        void getCategoriesByName_Success() {
            // Arrange
            String searchName = "Test";
            List<Categories> categories = Arrays.asList(category);
            Page<Categories> categoryPage = new PageImpl<>(categories, pageable, categories.size());
            when(categoriesRepository.findByNameContainingIgnoreCase(searchName, pageable))
                .thenReturn(categoryPage);
            when(categoriesMapper.toResponse(any(Categories.class))).thenReturn(categoriesResponse);

            // Act
            Page<CategoriesResponse> result = categoriesService.getCategoriesByName(searchName, pageable);

            // Assert
            assertNotNull(result);
            assertFalse(result.getContent().isEmpty());
            assertEquals(categoriesResponse.getName(), result.getContent().get(0).getName());
            verify(categoriesRepository).findByNameContainingIgnoreCase(searchName, pageable);
            verify(categoriesMapper, times(categories.size())).toResponse(any());
        }

        @Test
        @DisplayName("Should throw RuntimeException when search result is null")
        void getCategoriesByName_NullResult() {
            // Arrange
            String searchName = "NonExistent";
            when(categoriesRepository.findByNameContainingIgnoreCase(searchName, pageable))
                .thenReturn(null);

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoriesService.getCategoriesByName(searchName, pageable)
            );
            assertEquals("Categories not found", exception.getMessage());
            verify(categoriesRepository).findByNameContainingIgnoreCase(searchName, pageable);
            verify(categoriesMapper, never()).toResponse(any());
        }
    }
}