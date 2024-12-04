package com.app.catalogmanager.Service;

import com.app.catalogmanager.DTO.request.CategoriesRequest;
import com.app.catalogmanager.DTO.response.CategoriesResponse;
import com.app.catalogmanager.Entity.Categories;
import com.app.catalogmanager.Mapper.CategoriesMapper;
import com.app.catalogmanager.Repository.CategoriesRepository;
import com.app.catalogmanager.Service.impl.CategoriesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    private CategoriesRequest categoriesRequest;
    private Categories category;
    private CategoriesResponse categoriesResponse;

    @BeforeEach
    void setUp() {
        // Initialize test data
        categoriesRequest = new CategoriesRequest();
        categoriesRequest.setName("Test Category");

        category = new Categories();
        category.setId(1L);
        category.setName("Test Category");

        categoriesResponse = new CategoriesResponse();
        categoriesResponse.setName("Test Category");
    }

    @Nested
    @DisplayName("Create Categories Tests")
    class CreateCategoriesTests {

        @Test
        @DisplayName("Should successfully create a category")
        void createCategories_Success() {
            // Arrange
            when(categoriesMapper.toEntity(any(CategoriesRequest.class))).thenReturn(category);
            when(categoriesRepository.save(any(Categories.class))).thenReturn(category);
            when(categoriesMapper.toResponse(any(Categories.class))).thenReturn(categoriesResponse);

            // Act
            CategoriesResponse result = categoriesService.createCategories(categoriesRequest);

            // Assert
            assertNotNull(result);
            assertEquals(categoriesResponse.getName(), result.getName());

            verify(categoriesMapper).toEntity(categoriesRequest);
            verify(categoriesRepository).save(category);
            verify(categoriesMapper).toResponse(category);
        }

        @Test
        @DisplayName("Should throw exception when request is null")
        void createCategories_NullRequest() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> 
                categoriesService.createCategories(null)
            );
            
            verify(categoriesRepository, never()).save(any());
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
            when(categoriesRepository.findById(id)).thenReturn(Optional.of(category));
            when(categoriesRepository.save(any(Categories.class))).thenReturn(category);
            when(categoriesMapper.toResponse(any(Categories.class))).thenReturn(categoriesResponse);

            // Act
            CategoriesResponse result = categoriesService.updateCategories(id, categoriesRequest);

            // Assert
            assertNotNull(result);
            assertEquals(categoriesResponse.getName(), result.getName());

            verify(categoriesRepository).findById(id);
            verify(categoriesRepository).save(category);
            verify(categoriesMapper).toResponse(category);
        }

        @Test
        @DisplayName("Should throw exception when category not found")
        void updateCategories_CategoryNotFound() {
            // Arrange
            Long id = 1L;
            when(categoriesRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RuntimeException.class, () -> 
                categoriesService.updateCategories(id, categoriesRequest)
            );

            verify(categoriesRepository).findById(id);
            verify(categoriesRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw exception when id is null")
        void updateCategories_NullId() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> 
                categoriesService.updateCategories(null, categoriesRequest)
            );

            verify(categoriesRepository, never()).findById(any());
            verify(categoriesRepository, never()).save(any());
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
            when(categoriesRepository.findById(id)).thenReturn(Optional.of(category));
            doNothing().when(categoriesRepository).delete(any(Categories.class));

            // Act
            boolean result = categoriesService.deleteCategories(id);

            // Assert
            assertTrue(result);
            verify(categoriesRepository).findById(id);
            verify(categoriesRepository).delete(category);
        }

        @Test
        @DisplayName("Should return false when category not found")
        void deleteCategories_CategoryNotFound() {
            // Arrange
            Long id = 1L;
            when(categoriesRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            boolean result = categoriesService.deleteCategories(id);

            // Assert
            assertFalse(result);
            verify(categoriesRepository).findById(id);
            verify(categoriesRepository, never()).delete(any());
        }

        @Test
        @DisplayName("Should throw exception when id is null")
        void deleteCategories_NullId() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> 
                categoriesService.deleteCategories(null)
            );

            verify(categoriesRepository, never()).findById(any());
            verify(categoriesRepository, never()).delete(any());
        }
    }
}
