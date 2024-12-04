package com.app.catalogmanager.Entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designation")
    @NotBlank(message = "Designation is required")
    private String designation;

    @Column(name = "prix")
    @NotNull(message = "Prix is required")
    @Min(value = 0, message = "Prix must be greater than 0")
    private double prix;

    @Column(name = "quantite")
    @NotNull(message = "Quantite is required")
    @Min(value = 0, message = "Quantite must be greater than 0")
    private int quantite;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    @NotNull(message = "Categorie is required")
    private Categories categorie;

    
}
