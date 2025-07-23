package com.example.producto.service;

import com.example.producto.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto crearProducto(Producto producto);
    Optional<Producto> obtenerProductoPorId(Long id);
    List<Producto> listarProductos();
}
