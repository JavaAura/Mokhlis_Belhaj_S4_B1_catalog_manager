package com.app.catalogmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.catalogmanager.Entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    
   Page<Categories> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
