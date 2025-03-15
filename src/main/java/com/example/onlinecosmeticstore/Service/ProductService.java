package com.example.onlinecosmeticstore.Service;

import com.example.onlinecosmeticstore.Entity.Category;
import com.example.onlinecosmeticstore.Entity.Product;
import com.example.onlinecosmeticstore.Entity.Supplier;
import com.example.onlinecosmeticstore.Repository.CategoryRepository;
import com.example.onlinecosmeticstore.Repository.ProductRepository;
import com.example.onlinecosmeticstore.Repository.SupplierRepository;
import com.example.onlinecosmeticstore.dto.ProductDTO;
import com.example.onlinecosmeticstore.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Product product = productMapper.toEntity(productDTO);
        product.setCategory(category);
        product.setSupplier(supplier);

        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        product.setSupplier(supplier);

        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}