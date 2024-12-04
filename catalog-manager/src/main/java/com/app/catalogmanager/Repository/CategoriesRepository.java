package com.app.catalogmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.catalogmanager.Entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    
}
