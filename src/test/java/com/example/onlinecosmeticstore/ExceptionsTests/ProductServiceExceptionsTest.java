package com.example.OnlineCosmeticStore.ExceptionsTests;

import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Repository.ProductRepository;
import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import com.example.OnlineCosmeticStore.Service.ProductService;
import com.example.OnlineCosmeticStore.dto.ProductDTO;
import com.example.OnlineCosmeticStore.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class ProductServiceExceptionsTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenProductNotFoundById() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            productService.getProductById(productId);
        });

        assertEquals("Product not found with ID: " + productId, thrown.getMessage());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenCategoryNotFoundDuringProductCreation() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(1L);
        productDTO.setSupplierId(1L);

        when(categoryRepository.findById(productDTO.getCategoryId())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("Category not found", thrown.getMessage());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenSupplierNotFoundDuringProductCreation() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(1L);
        productDTO.setSupplierId(1L);

        Category category = new Category();
        when(categoryRepository.findById(productDTO.getCategoryId())).thenReturn(Optional.of(category));

        when(supplierRepository.findById(productDTO.getSupplierId())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("Supplier not found", thrown.getMessage());
    }

}
