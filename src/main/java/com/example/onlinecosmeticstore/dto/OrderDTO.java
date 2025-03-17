package com.example.OnlineCosmeticStore.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    private String status;
    private Set<Long> productIds;
}
