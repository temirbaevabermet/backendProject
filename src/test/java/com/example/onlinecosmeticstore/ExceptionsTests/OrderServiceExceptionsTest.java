package com.example.OnlineCosmeticStore.ExceptionsTests;

import com.example.OnlineCosmeticStore.Repository.OrderRepository;
import com.example.OnlineCosmeticStore.Service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class OrderServiceExceptionsTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenOrderNotFoundById() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            orderService.getOrderById(orderId);
        });

        assertEquals("Order not found with ID: " + orderId, thrown.getMessage());
    }

}
