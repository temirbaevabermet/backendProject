package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // Создаем несколько заказов для тестирования
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .status("PENDING")
                .build();
        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .status("COMPLETED")
                .build();
        Order order3 = Order.builder()
                .orderDate(LocalDate.now())
                .status("PENDING")
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
    }

    @Test
    void testFindByStatus() {

        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");


        assertEquals(2, pendingOrders.size(), "Should return 2 orders with status 'PENDING'");


        pendingOrders.forEach(order -> assertEquals("PENDING", order.getStatus()));
    }

    @Test
    void testFindByStatusEmpty() {
        List<Order> completedOrders = orderRepository.findByStatus("CANCELLED");

        assertEquals(0, completedOrders.size(), "Should return no orders with status 'CANCELLED'");
    }
}