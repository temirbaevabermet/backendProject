package com.example.OnlineCosmeticStore.dto;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    @NotNull(message = "Product name cannot be empty")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    private String description;

    @NotNull(message = "Product price cannot be null")
    @Positive(message = "The price of the product must be a positive number")
    private BigDecimal price;

    @NotNull(message = "Product category cannot be empty")
    private Long categoryId;

    @NotNull(message = "Product supplier cannot be empty")
    private Long supplierId;
}