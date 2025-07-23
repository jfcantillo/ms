package com.example.producto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;

    // Getters y Setters
}