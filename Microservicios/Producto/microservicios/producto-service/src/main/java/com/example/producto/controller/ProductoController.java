package com.example.producto.controller;

import com.example.producto.model.Producto;
import com.example.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Producto> listProductos() {
        return productoService.listarProductos();
    }
}
