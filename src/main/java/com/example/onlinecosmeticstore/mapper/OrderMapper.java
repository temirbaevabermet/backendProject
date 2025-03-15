package com.example.onlinecosmeticstore.mapper;

import com.example.onlinecosmeticstore.dto.OrderDTO;
import com.example.onlinecosmeticstore.Entity.Order;
import com.example.onlinecosmeticstore.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "productIds", expression = "java(order.getProducts().stream().map(Product::getId).collect(Collectors.toSet()))")
    OrderDTO toDTO(Order order);

    @Mapping(target = "products", ignore = true) // Продукты подставляем отдельно в сервисе
    Order toEntity(OrderDTO orderDTO);
}