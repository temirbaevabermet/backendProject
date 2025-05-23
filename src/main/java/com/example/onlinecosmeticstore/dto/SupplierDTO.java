package com.example.OnlineCosmeticStore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    private Long id;

    @NotNull(message = "Supplier name cannot be empty")
    private String name;

    @Email(message = "Invalid contact format")
    private String contactInfo;
}
