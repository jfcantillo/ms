package com.ejemplo.inventario;

import com.ejemplo.inventario.repository.InventarioRepository;
import com.ejemplo.inventario.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioRepository inventarioRepository;

    @MockBean
    private ProductoService productoService;

    @Test
    void testCompraExitosa() throws Exception {
        String json = "{ \"productoId\": 1, \"cantidad\": 2 }";
        when(productoService.productoExiste(1L)).thenReturn(true);

        mockMvc.perform(post("/inventario/compras")
                .contentType("application/json")
                .header("X-API-KEY", "supersecreta123")
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testCompraProductoInexistente() throws Exception {
        String json = "{ \"productoId\": 999, \"cantidad\": 2 }";
        when(productoService.productoExiste(999L)).thenReturn(false);

        mockMvc.perform(post("/inventario/compras")
                .contentType("application/json")
                .header("X-API-KEY", "supersecreta123")
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
