package com.app.catalogmanager.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.catalogmanager.Entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    
   Page<Categories> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
