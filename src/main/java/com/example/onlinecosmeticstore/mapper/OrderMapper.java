package com.example.onlinecosmeticstore.mapper;

import com.example.onlinecosmeticstore.dto.OrderDTO;
import com.example.onlinecosmeticstore.Entity.Order;
import com.example.onlinecosmeticstore.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "products", target = "productIds", qualifiedByName = "mapProductsToIds")
    OrderDTO toDTO(Order order);

    @Mapping(source = "productIds", target = "products", qualifiedByName = "mapIdsToProducts")
    Order toEntity(OrderDTO orderDTO);

    @Named("mapProductsToIds")
    default Set<Long> mapProductsToIds(Set<Product> products) {
        return products.stream().map(Product::getId).collect(Collectors.toSet());
    }

    @Named("mapIdsToProducts")
    default Set<Product> mapIdsToProducts(Set<Long> productIds) {
        return productIds.stream().map(id -> {
            Product product = new Product();
            product.setId(id);
            return product;
        }).collect(Collectors.toSet());
    }
}