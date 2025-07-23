package com.example.inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Inventario {
    @Id
    private Long productoId;
    private int cantidad;

    // Getters y Setters
}