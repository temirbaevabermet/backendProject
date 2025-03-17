package com.example.OnlineCosmeticStore.mapper;

import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.example.OnlineCosmeticStore.Entity.Order;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setProductIds(order.getProducts().stream().map(p -> p.getId()).collect(Collectors.toSet()));
        return dto;
    }

    public static Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        return order;
    }
}