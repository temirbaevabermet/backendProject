package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.SupplierService;
import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Validated
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Operation(summary = "Get all suppliers", description = "Retrieve a list of all suppliers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of suppliers")
    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @Operation(summary = "Get a supplier by ID", description = "Retrieve a supplier by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the supplier")
    @ApiResponse(responseCode = "404", description = "Supplier not found")
    @GetMapping("/{id}")
    public SupplierDTO getSupplier(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @Operation(summary = "Create a new supplier", description = "Create a new supplier with the provided details")
    @ApiResponse(responseCode = "201", description = "Successfully created the supplier")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody @Valid SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
    }

    @Operation(summary = "Delete a supplier by ID", description = "Delete a supplier by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the supplier")
    @ApiResponse(responseCode = "404", description = "Supplier not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}