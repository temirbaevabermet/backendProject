package com.example.onlinecosmeticstore.dto;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private String status;
    private Set<Long> productIds;
}
