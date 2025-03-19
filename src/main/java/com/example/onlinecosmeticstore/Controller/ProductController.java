package com.example.OnlineCosmeticStore.Controller;

import com.example.OnlineCosmeticStore.Service.ProductService;
import com.example.OnlineCosmeticStore.dto.ProductDTO;
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
@RequestMapping("/api/products")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products")
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get a product by ID", description = "Retrieve a product by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the product")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponse(responseCode = "201", description = "Successfully created the product")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a product by ID", description = "Delete a product by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the product")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}