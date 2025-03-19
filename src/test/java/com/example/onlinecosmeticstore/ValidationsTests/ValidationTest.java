package com.example.OnlineCosmeticStore.ValidationsTests;

import com.example.OnlineCosmeticStore.dto.CategoryDTO;
import com.example.OnlineCosmeticStore.dto.ProductDTO;
import com.example.OnlineCosmeticStore.dto.OrderDTO;
import com.example.OnlineCosmeticStore.dto.SupplierDTO;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCategoryDTOValidation() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(null); // Invalid - Name is null
        categoryDTO.setDescription(null); // Invalid - Description is empty

        Set<ConstraintViolation<CategoryDTO>> violations = validator.validate(categoryDTO);

        assertFalse(violations.isEmpty(), "Validation should fail");
        assertEquals(2, violations.size(), "There should be two validation errors");

        violations.forEach(violation -> {
            if (violation.getPropertyPath().toString().equals("name")) {
                assertEquals("Category name cannot be empty", violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("description")) {
                assertEquals("Category description cannot be empty", violation.getMessage());
            }
        });
    }

    @Test
    public void testProductDTOValidation() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Pro"); // Valid name length
        productDTO.setPrice(null); // Invalid - Price is null
        productDTO.setCategoryId(null); // Invalid - Category is null
        productDTO.setSupplierId(null); // Invalid - Supplier is null

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);

        assertFalse(violations.isEmpty(), "Validation should fail");
        assertEquals(3, violations.size(), "There should be three validation errors");

        violations.forEach(violation -> {
            if (violation.getPropertyPath().toString().equals("price")) {
                assertEquals("Product price cannot be null", violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("categoryId")) {
                assertEquals("Product category cannot be empty", violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("supplierId")) {
                assertEquals("Product supplier cannot be empty", violation.getMessage());
            }
        });
    }

    @Test
    public void testOrderDTOValidation() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(null); // Valid, assuming null is acceptable
        orderDTO.setStatus(null); // Invalid - Status is empty
        orderDTO.setProductIds(Set.of()); // Invalid - Product list is empty

        Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);

        assertFalse(violations.isEmpty(), "Validation should fail");
        assertEquals(2, violations.size(), "There should be two validation errors");

        // Check specific error messages
        violations.forEach(violation -> {
            if (violation.getPropertyPath().toString().equals("status")) {
                assertEquals("Order status cannot be empty", violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("productIds")) {
                assertEquals("The product list cannot be empty", violation.getMessage());
            }
        });
    }

    @Test
    public void testSupplierDTOValidation() {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setName(null); // Invalid - Name is null
        supplierDTO.setContactInfo("invalid-email"); // Invalid - Email is not correct

        Set<ConstraintViolation<SupplierDTO>> violations = validator.validate(supplierDTO);

        assertFalse(violations.isEmpty(), "Validation should fail");
        assertEquals(2, violations.size(), "There should be two validation errors");

        // Check specific error messages
        violations.forEach(violation -> {
            if (violation.getPropertyPath().toString().equals("name")) {
                assertEquals("Supplier name cannot be empty", violation.getMessage());
            }
            if (violation.getPropertyPath().toString().equals("contactInfo")) {
                assertEquals("Invalid contact format", violation.getMessage());
            }
        });
    }
}