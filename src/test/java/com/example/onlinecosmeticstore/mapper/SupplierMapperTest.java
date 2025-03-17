package com.example.OnlineCosmeticStore.mapper;

import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierMapperTest {

    @Test
    void testToDto() {
        // Arrange
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Beauty Supplies Ltd.");
        supplier.setContactInfo("contact@beautysupplies.com");

        // Act
        SupplierDTO dto = SupplierMapper.toDto(supplier);

        // Assert
        assertNotNull(dto);
        assertEquals(supplier.getId(), dto.getId());
        assertEquals(supplier.getName(), dto.getName());
        assertEquals(supplier.getContactInfo(), dto.getContactInfo());
    }

    @Test
    void testToEntity() {
        // Arrange
        SupplierDTO dto = new SupplierDTO();
        dto.setId(1L);
        dto.setName("Beauty Supplies Ltd.");
        dto.setContactInfo("contact@beautysupplies.com");

        // Act
        Supplier supplier = SupplierMapper.toEntity(dto);

        // Assert
        assertNotNull(supplier);
        assertEquals(dto.getId(), supplier.getId());
        assertEquals(dto.getName(), supplier.getName());
        assertEquals(dto.getContactInfo(), supplier.getContactInfo());
    }
}
