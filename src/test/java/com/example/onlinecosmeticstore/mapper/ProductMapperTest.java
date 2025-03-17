package com.example.OnlineCosmeticStore.mapper;

import com.example.OnlineCosmeticStore.dto.ProductDTO;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void testToDto() {
        // Arrange
        Category category = new Category();
        category.setId(1L);

        Supplier supplier = new Supplier();
        supplier.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Lipstick");
        product.setDescription("Red Lipstick");
        product.setPrice(BigDecimal.valueOf(19.99));
        product.setCategory(category);
        product.setSupplier(supplier);

        // Act
        ProductDTO dto = ProductMapper.toDto(product);

        // Assert
        assertNotNull(dto);
        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getCategory().getId(), dto.getCategoryId());
        assertEquals(product.getSupplier().getId(), dto.getSupplierId());
    }

    @Test
    void testToEntity() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Lipstick");
        dto.setDescription("Red Lipstick");
        dto.setPrice(BigDecimal.valueOf(19.99));

        // Act
        Product product = ProductMapper.toEntity(dto);

        // Assert
        assertNotNull(product);
        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
    }
}