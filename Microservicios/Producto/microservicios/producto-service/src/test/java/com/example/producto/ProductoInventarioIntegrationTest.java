package com.ejemplo.integracion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductoInventarioIntegrationTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testProductoEInventarioFlujo() {
        // Crear producto
        String productoJson = "{ \"nombre\": \"Mouse\", \"precio\": 25.0 }";
        var response = restTemplate.postForEntity("http://localhost:8081/productos", productoJson, String.class);
        assertEquals(201, response.getStatusCodeValue());

        // Suponemos que el inventario ya está cargado (mock o inserción directa)

        // Ejecutar compra
        String compraJson = "{ \"productoId\": 1, \"cantidad\": 1 }";
        var compraResp = restTemplate.postForEntity("http://localhost:8082/inventario/compras", compraJson, String.class);
        assertEquals(200, compraResp.getStatusCodeValue());
    }
}
