package com.app.catalogmanager.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.catalogmanager.Entity.Produits;

@Repository

public interface ProduitsRepository extends JpaRepository<Produits, Long> {

     Page<Produits> findByDesignationContainingIgnoreCase(String designation, Pageable pageable);

}
