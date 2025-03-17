package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.OrderService;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);  // Убедитесь, что возвращается статус 201
    }

    // Delete an order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();  // Возвращаем статус 204 No Content
    }
}