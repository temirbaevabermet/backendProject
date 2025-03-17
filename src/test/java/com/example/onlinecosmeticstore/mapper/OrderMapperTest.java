package com.example.OnlineCosmeticStore.mapper;

import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.example.OnlineCosmeticStore.Entity.Order;
import com.example.OnlineCosmeticStore.Entity.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void testToDto() {
        // Arrange
        Set<Product> products = new HashSet<>();
        Product product = new Product();
        product.setId(1L);
        products.add(product);

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDate.now());
        order.setStatus("Pending");
        order.setProducts(products);

        // Act
        OrderDTO dto = OrderMapper.toDto(order);

        // Assert
        assertNotNull(dto);
        assertEquals(order.getId(), dto.getId());
        assertEquals(order.getOrderDate(), dto.getOrderDate());
        assertEquals(order.getStatus(), dto.getStatus());
        assertTrue(dto.getProductIds().contains(product.getId()));
        assertEquals(1, dto.getProductIds().size());
    }

    @Test
    void testToEntity() {
        // Arrange
        OrderDTO dto = new OrderDTO();
        dto.setId(1L);
        dto.setOrderDate(LocalDate.now());
        dto.setStatus("Pending");

        // Act
        Order order = OrderMapper.toEntity(dto);

        // Assert
        assertNotNull(order);
        assertEquals(dto.getId(), order.getId());
        assertEquals(dto.getOrderDate(), order.getOrderDate());
        assertEquals(dto.getStatus(), order.getStatus());
    }
}