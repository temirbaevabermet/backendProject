package com.example.OnlineCosmeticStore.Integration;
import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import com.example.OnlineCosmeticStore.dto.ProductDTO;
import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  // Внедрение MockMvc для выполнения HTTP-запросов

    @Autowired
    private ProductService productService;  // Внедрение реального сервиса

    @Autowired
    private CategoryRepository categoryRepository;  // Репозиторий для получения категорий

    @Autowired
    private SupplierRepository supplierRepository;  // Репозиторий для получения поставщиков

    private ObjectMapper objectMapper;

    private Long categoryId;
    private Long supplierId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        Category category = categoryRepository.findByName("Skincare").orElseThrow(() -> new RuntimeException("Category not found"));
        Supplier supplier = supplierRepository.findByName("NARS").orElseThrow(() -> new RuntimeException("Supplier not found"));

        categoryId = category.getId();
        supplierId = supplier.getId();
    }

    @Test
    void testCreateProduct() throws Exception {
        // Подготовка данных для теста
        ProductDTO productDTO = ProductDTO.builder()
                .name("New Product")
                .description("New Product Description")
                .price(new BigDecimal("15.99"))
                .categoryId(categoryId)  // Используем ID категории, полученный из базы
                .supplierId(supplierId)  // Используем ID поставщика, полученный из базы
                .build();

        // Выполнение запроса на создание продукта
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())  // Ожидаем статус 201
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.price").value(15.99));
    }

    @Test
    void testGetProductById() throws Exception {
        Long productId = 1L;  // Используем реальный ID продукта из вашей базы данных

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())  // Ожидаем статус 200
                .andExpect(jsonPath("$.id").value(productId));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long productId = 1L;  // Используем реальный ID продукта из вашей базы данных

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());  // Ожидаем статус 204 (No Content)
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())  // Ожидаем статус 200
                .andExpect(jsonPath("$").isArray())  // Ожидаем, что вернется массив
                .andExpect(jsonPath("$[0].name").exists());
    }
}
