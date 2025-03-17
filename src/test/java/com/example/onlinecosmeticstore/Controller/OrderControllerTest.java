package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.OrderService;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Настройка ObjectMapper для поддержки LocalDate
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Регистрация JavaTimeModule

        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testGetAllOrders() throws Exception {
        OrderDTO order1 = new OrderDTO(1L, LocalDate.now(), "Pending", new HashSet<>());
        OrderDTO order2 = new OrderDTO(2L, LocalDate.now(), "Shipped", new HashSet<>());
        List<OrderDTO> orders = List.of(order1, order2);

        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].status").value("Shipped"));

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById() throws Exception {
        OrderDTO orderDTO = new OrderDTO(1L, LocalDate.now(), "Pending", new HashSet<>());
        when(orderService.getOrderById(anyLong())).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("Pending"));

        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    void testCreateOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO(1L, LocalDate.now(), "Pending", new HashSet<>());
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("Pending"));

        verify(orderService, times(1)).createOrder(any(OrderDTO.class));
    }

    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(anyLong());

        mockMvc.perform(delete("/api/orders/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder(1L);
    }
}