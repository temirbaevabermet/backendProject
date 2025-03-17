package com.example.OnlineCosmeticStore.Service;

import com.example.OnlineCosmeticStore.Entity.Order;
import com.example.OnlineCosmeticStore.Repository.OrderRepository;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.example.OnlineCosmeticStore.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Order order = orderRepository.findById(id).orElseThrow();
        return OrderMapper.toDto(order);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDto(savedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}