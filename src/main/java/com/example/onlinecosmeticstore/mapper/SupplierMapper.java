package com.example.onlinecosmeticstore.mapper;

import com.example.onlinecosmeticstore.dto.SupplierDTO;
import com.example.onlinecosmeticstore.Entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDTO toDTO(Supplier supplier);
    Supplier toEntity(SupplierDTO supplierDTO);
}