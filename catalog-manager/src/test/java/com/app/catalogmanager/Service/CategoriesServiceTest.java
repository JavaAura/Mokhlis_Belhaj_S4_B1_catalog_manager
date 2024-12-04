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
        categoriesRequest.setDescription("Description Test");

        category = new Categories();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Description Test");
        categoriesResponse = new CategoriesResponse();
        categoriesResponse.setName("Test Category");
        categoriesResponse.setDescription("Description Test");
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
            assertEquals(categoriesResponse.getDescription(), result.getDescription());

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
            assertEquals(categoriesResponse.getDescription(), result.getDescription());
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

        @Nested
    @DisplayName("Get All Categories Tests")
    class GetAllCategoriesTests {

        @Test
        @DisplayName("Should return page of categories")
        void allCategories_Success() {
            // Arrange
            Pageable pageable = PageRequest.of(0, 10);
            List<Categories> categoryList = Arrays.asList(
                category,
                Categories.builder().id(2L).name("Category 2").description("Description 2").build()
            );
            Page<Categories> categoryPage = new PageImpl<>(categoryList, pageable, categoryList.size());
            when(categoriesRepository.findAll(pageable)).thenReturn(categoryPage);
            when(categoriesMapper.toResponse(any(Categories.class)))
                .thenAnswer(invocation -> {
                    Categories cat = invocation.getArgument(0);
                    return CategoriesResponse.builder()
                            .name(cat.getName())
                            .description(cat.getDescription())
                            .build();
                });

            // Act
            Page<CategoriesResponse> result = categoriesService.allcategories(pageable);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            assertEquals(2, result.getContent().size());
            verify(categoriesRepository).findAll(pageable);
            verify(categoriesMapper, times(2)).toResponse(any(Categories.class));
        }
    }

    @Nested
    @DisplayName("Get Categories By Name Tests")
    class GetCategoriesByNameTests {

        @Test
        @DisplayName("Should return page of categories when searching by name")
        void getCategoriesByName_Success() {
            // Arrange
            String searchName = "cat";
            Pageable pageable = PageRequest.of(0, 10);
            List<Categories> categoryList = Arrays.asList(
                category,
                Categories.builder().id(2L).name("Category 2").description("Description 2").build()
            );
            Page<Categories> categoryPage = new PageImpl<>(categoryList, pageable, categoryList.size());
            when(categoriesRepository.findByNameContainingIgnoreCase(searchName, pageable))
                .thenReturn(categoryPage);
            when(categoriesMapper.toResponse(any(Categories.class)))
                .thenAnswer(invocation -> {
                    Categories cat = invocation.getArgument(0);
                    return CategoriesResponse.builder()
                            .name(cat.getName())
                            .description(cat.getDescription())
                            .build();
                });

            // Act
            Page<CategoriesResponse> result = categoriesService.getCategoriesByName(searchName, pageable);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            assertEquals(2, result.getContent().size());
            verify(categoriesRepository).findByNameContainingIgnoreCase(searchName, pageable);
            verify(categoriesMapper, times(2)).toResponse(any(Categories.class));
        }

        @Test
        @DisplayName("Should throw RuntimeException when no categories found")
        void getCategoriesByName_NotFound() {
            // Arrange
            String searchName = "nonexistent";
            Pageable pageable = PageRequest.of(0, 10);
            when(categoriesRepository.findByNameContainingIgnoreCase(searchName, pageable))
                .thenReturn(null);

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () ->
                categoriesService.getCategoriesByName(searchName, pageable)
            );
            assertEquals("Categories not found", exception.getMessage());
            verify(categoriesRepository).findByNameContainingIgnoreCase(searchName, pageable);
            verify(categoriesMapper, never()).toResponse(any(Categories.class));
        }

        @Test
        @DisplayName("Should return empty page when no matches found")
        void getCategoriesByName_EmptyResult() {
            // Arrange
            String searchName = "xyz";
            Pageable pageable = PageRequest.of(0, 10);
            Page<Categories> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
            when(categoriesRepository.findByNameContainingIgnoreCase(searchName, pageable))
                .thenReturn(emptyPage);

            // Act
            Page<CategoriesResponse> result = categoriesService.getCategoriesByName(searchName, pageable);

            // Assert
            assertNotNull(result);
            assertTrue(result.getContent().isEmpty());
            assertEquals(0, result.getTotalElements());
            verify(categoriesRepository).findByNameContainingIgnoreCase(searchName, pageable);
            verify(categoriesMapper, never()).toResponse(any(Categories.class));
        }
    }
}
