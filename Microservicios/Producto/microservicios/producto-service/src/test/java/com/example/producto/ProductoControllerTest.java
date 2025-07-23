package com.ejemplo.producto;

import com.ejemplo.producto.model.Producto;
import com.ejemplo.producto.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    void testCrearProducto() throws Exception {
        String json = "{ \"nombre\": \"Teclado\", \"precio\": 49.99 }";

        mockMvc.perform(post("/productos")
                .contentType("application/json")
                .header("X-API-KEY", "supersecreta123")
                .content(json))
                .andExpect(status().isCreated());
    }
}
