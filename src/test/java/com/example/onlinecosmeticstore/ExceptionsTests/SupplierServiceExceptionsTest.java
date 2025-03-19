package com.example.OnlineCosmeticStore.ExceptionsTests;

import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import com.example.OnlineCosmeticStore.Service.SupplierService;
import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class SupplierServiceExceptionsTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenSupplierNotFoundById() {
        Long supplierId = 1L;

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            supplierService.getSupplierById(supplierId);
        });

        assertEquals("Supplier not found with ID: " + supplierId, thrown.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenSupplierNameIsNullOrEmpty() {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setName(""); // Empty name

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            supplierService.createSupplier(supplierDTO);
        });

        assertEquals("Supplier name cannot be null or empty", thrown.getMessage());
    }

}