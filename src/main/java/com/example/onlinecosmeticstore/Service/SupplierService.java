package com.example.onlinecosmeticstore.Service;

import com.example.onlinecosmeticstore.Entity.Supplier;
import com.example.onlinecosmeticstore.Repository.SupplierRepository;
import com.example.onlinecosmeticstore.dto.SupplierDTO;
import com.example.onlinecosmeticstore.mapper.SupplierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SupplierDTO getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(supplier);
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setName(supplierDTO.getName());
        supplier.setContactInfo(supplierDTO.getContactInfo());

        supplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
