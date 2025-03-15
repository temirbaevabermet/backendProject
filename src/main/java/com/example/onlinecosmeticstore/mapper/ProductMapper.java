package com.example.onlinecosmeticstore.mapper;

import com.example.onlinecosmeticstore.dto.ProductDTO;
import com.example.onlinecosmeticstore.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductDTO toDTO(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "supplierId", target = "supplier.id")
    Product toEntity(ProductDTO productDTO);
}
