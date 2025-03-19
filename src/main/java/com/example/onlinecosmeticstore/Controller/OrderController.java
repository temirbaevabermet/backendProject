package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.OrderService;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of orders")
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Get an order by ID", description = "Retrieve an order by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the order")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Create a new order", description = "Create a new order with the provided details")
    @ApiResponse(responseCode = "201", description = "Successfully created the order")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an order by ID", description = "Delete an order by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the order")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}