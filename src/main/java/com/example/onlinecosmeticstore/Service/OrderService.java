package com.example.OnlineCosmeticStore.Service;

import com.example.OnlineCosmeticStore.Entity.Order;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Repository.OrderRepository;
import com.example.OnlineCosmeticStore.Repository.ProductRepository;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.example.OnlineCosmeticStore.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
        return OrderMapper.toDto(order);
    }

    @Autowired
    private ProductRepository productRepository;

    public OrderDTO createOrder(OrderDTO dto) {
        Order order = OrderMapper.toEntity(dto);

        // Найти реальные Product по ID
        Set<Product> products = dto.getProductIds().stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id)))
                .collect(Collectors.toSet());

        order.setProducts(products);

        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}