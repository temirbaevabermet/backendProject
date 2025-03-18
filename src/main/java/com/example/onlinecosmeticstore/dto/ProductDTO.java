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
    @NotNull(message = "Название продукта не может быть пустым")
    @Size(min = 3, max = 100, message = "Название продукта должно быть между 3 и 100 символами")
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