package com.example.OnlineCosmeticStore.Service;

import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import com.example.OnlineCosmeticStore.mapper.SupplierMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(SupplierMapper::toDto)
                .collect(Collectors.toList());
    }

    public SupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found with ID: " + id));
        return SupplierMapper.toDto(supplier);
    }

    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        if (supplierDTO.getName() == null || supplierDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty");
        }
        Supplier supplier = SupplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return SupplierMapper.toDto(savedSupplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}