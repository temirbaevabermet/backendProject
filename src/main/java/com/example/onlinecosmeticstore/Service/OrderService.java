package com.example.onlinecosmeticstore.Service;

import com.example.onlinecosmeticstore.Entity.Order;
import com.example.onlinecosmeticstore.Entity.Product;
import com.example.onlinecosmeticstore.Repository.OrderRepository;
import com.example.onlinecosmeticstore.Repository.ProductRepository;
import com.example.onlinecosmeticstore.dto.OrderDTO;
import com.example.onlinecosmeticstore.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Set<Product> products = productRepository.findAllById(orderDTO.getProductIds())
                .stream().collect(Collectors.toSet());

        Order order = orderMapper.toEntity(orderDTO);
        order.setProducts(products);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Set<Product> products = productRepository.findAllById(orderDTO.getProductIds())
                .stream().collect(Collectors.toSet());

        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());
        order.setProducts(products);

        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
