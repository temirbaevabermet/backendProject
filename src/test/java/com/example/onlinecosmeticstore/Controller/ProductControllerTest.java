package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.ProductService;
import com.example.OnlineCosmeticStore.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    // Test for GET all products
    @Test
    void testGetAllProducts() throws Exception {
        ProductDTO product1 = new ProductDTO(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 1L, 1L);
        ProductDTO product2 = new ProductDTO(2L, "Product 2", "Description 2", new BigDecimal("15.00"), 2L, 2L);
        List<ProductDTO> products = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Product 2"));

        verify(productService, times(1)).getAllProducts();
    }

    // Test for GET product by ID
    @Test
    void testGetProductById() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 1L, 1L);

        when(productService.getProductById(anyLong())).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product 1"));

        verify(productService, times(1)).getProductById(1L);
    }

    // Test for POST create new product
    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 1L, 1L);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())  // Проверяем, что статус 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product 1"));

        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    // Тест для DELETE запроса
    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyLong());

        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}