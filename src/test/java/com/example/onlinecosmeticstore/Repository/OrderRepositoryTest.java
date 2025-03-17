package com.example.OnlineCosmeticStore.Repository;

import com.example.OnlineCosmeticStore.Entity.Order;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Entity.Supplier;
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
        // Выполняем поиск по статусу "PENDING"
        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");

        // Проверяем, что количество найденных заказов соответствует ожиданиям
        assertEquals(2, pendingOrders.size(), "Should return 2 orders with status 'PENDING'");

        // Проверяем, что все найденные заказы имеют статус "PENDING"
        pendingOrders.forEach(order -> assertEquals("PENDING", order.getStatus()));
    }

    @Test
    void testFindByStatusEmpty() {
        // Выполняем поиск по статусу, которого нет
        List<Order> completedOrders = orderRepository.findByStatus("CANCELLED");

        // Проверяем, что результат пустой
        assertEquals(0, completedOrders.size(), "Should return no orders with status 'CANCELLED'");
    }
}