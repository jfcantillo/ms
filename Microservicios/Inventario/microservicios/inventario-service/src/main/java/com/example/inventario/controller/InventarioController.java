package com.example.inventario.controller;

import com.example.inventario.model.Inventario;
import com.example.inventario.model.CompraRequest;
import com.example.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/{productoId}")
    public ResponseEntity<Inventario> getInventario(@PathVariable Long productoId) {
        Optional<Inventario> inventario = inventarioRepository.findById(productoId);
        return inventario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long productoId, @RequestBody Inventario update) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findById(productoId);
        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setCantidad(update.getCantidad());
            return ResponseEntity.ok(inventarioRepository.save(inventario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/compras")
    public ResponseEntity<String> comprarProducto(@RequestBody CompraRequest request) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findById(request.getProductoId());
        if (inventarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Producto no encontrado en inventario");
        }

        Inventario inventario = inventarioOpt.get();
        if (inventario.getCantidad() < request.getCantidad()) {
            return ResponseEntity.badRequest().body("Inventario insuficiente");
        }

        // Validar que el producto existe en producto-service (mock HTTP call)
        try {
            String url = "http://producto-service:8080/productos/" + request.getProductoId();
            restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Producto no existe en producto-service");
        }

        inventario.setCantidad(inventario.getCantidad() - request.getCantidad());
        inventarioRepository.save(inventario);

        return ResponseEntity.ok("Compra realizada con éxito");
    }
}