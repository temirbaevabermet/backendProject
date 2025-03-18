package com.example.OnlineCosmeticStore.Integration;

import com.example.OnlineCosmeticStore.Entity.Order;
import com.example.OnlineCosmeticStore.Repository.OrderRepository;
import com.example.OnlineCosmeticStore.Service.OrderService;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Очистить репозиторий перед каждым тестом
        orderRepository.deleteAll();
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Создаем новый заказ
        OrderDTO newOrder = OrderDTO.builder()
                .orderDate(LocalDate.now())
                .status("PENDING")
                .productIds(Set.of(1L, 2L))  // Примерный список идентификаторов продуктов
                .build();

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isCreated())  // Проверка, что статус 201 (Created)
                .andExpect(jsonPath("$.status").value("PENDING"))  // Проверка правильности поля статус
                .andExpect(jsonPath("$.productIds").isArray());  // Проверка, что productIds это массив
    }

    @Test
    public void testGetOrderById() throws Exception {
        // Сначала создаем заказ для получения
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .status("PENDING")
                .products(Set.of())  // Добавьте реальные продукты
                .build();
        orderRepository.save(order);

        // Запрашиваем заказ по ID
        mockMvc.perform(get("/api/orders/" + order.getId()))
                .andExpect(status().isOk())  // Проверка, что статус 200 (OK)
                .andExpect(jsonPath("$.id").value(order.getId()))  // Проверка, что возвращается правильный ID
                .andExpect(jsonPath("$.status").value("PENDING"));  // Проверка статуса заказа
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // Создаем несколько заказов
        Order order1 = Order.builder()
                .orderDate(LocalDate.now())
                .status("PENDING")
                .products(Set.of())  // Добавьте реальные продукты
                .build();
        Order order2 = Order.builder()
                .orderDate(LocalDate.now())
                .status("COMPLETED")
                .products(Set.of())  // Добавьте реальные продукты
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);

        // Запрашиваем все заказы
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())  // Проверка, что статус 200 (OK)
                .andExpect(jsonPath("$[0].status").value("PENDING"))  // Проверка статуса первого заказа
                .andExpect(jsonPath("$[1].status").value("COMPLETED"));  // Проверка статуса второго заказа
    }

    @Test
    public void testDeleteOrder() throws Exception {
        // Создание заказа, чтобы убедиться, что он существует в базе данных
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setStatus("Pending");
        orderDTO.setProductIds(Set.of(1L, 2L)); // Пример с двумя продуктами

        // Сохраняем заказ через POST запрос
        MvcResult createResult = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String createdOrderResponse = createResult.getResponse().getContentAsString();
        OrderDTO createdOrder = objectMapper.readValue(createdOrderResponse, OrderDTO.class);

        // Получаем ID созданного заказа
        Long createdOrderId = createdOrder.getId();

        // Проверяем, существует ли заказ в базе данных перед удалением
        mockMvc.perform(get("/api/orders/{id}", createdOrderId))
                .andExpect(status().isOk());  // Убедитесь, что заказ существует

        // Удаляем заказ с созданным ID
        mockMvc.perform(delete("/api/orders/{id}", createdOrderId))
                .andExpect(status().isNoContent());  // Статус 204 - No Content
    }
}