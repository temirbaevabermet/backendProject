package com.example.OnlineCosmeticStore.mapper;

import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import com.example.OnlineCosmeticStore.Entity.Supplier;

public class SupplierMapper {
    public static SupplierDTO toDto(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setContactInfo(supplier.getContactInfo());
        return dto;
    }

    public static Supplier toEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setName(dto.getName());
        supplier.setContactInfo(dto.getContactInfo());
        return supplier;
    }
}