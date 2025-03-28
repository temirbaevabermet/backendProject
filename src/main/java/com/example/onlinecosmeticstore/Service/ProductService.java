package com.example.OnlineCosmeticStore.Service;

import com.example.OnlineCosmeticStore.Entity.Category;
import com.example.OnlineCosmeticStore.Entity.Product;
import com.example.OnlineCosmeticStore.Entity.Supplier;
import com.example.OnlineCosmeticStore.Repository.CategoryRepository;
import com.example.OnlineCosmeticStore.Repository.ProductRepository;
import com.example.OnlineCosmeticStore.Repository.SupplierRepository;
import com.example.OnlineCosmeticStore.dto.ProductDTO;
import com.example.OnlineCosmeticStore.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        return ProductMapper.toDto(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));


        Product product = ProductMapper.toEntity(productDTO);
        product.setCategory(category);
        product.setSupplier(supplier);


        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDto(savedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}