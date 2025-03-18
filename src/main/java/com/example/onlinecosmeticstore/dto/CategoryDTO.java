package com.example.OnlineCosmeticStore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotNull(message = "Category name cannot be empty")
    private String name;

    @NotNull(message = "Category description cannot be empty")
    private String description;
}