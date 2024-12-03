package com.app.catalogmanager.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true)
    @NotBlank(message = "Name is required")
    private String name;
}
