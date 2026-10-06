package com.pc_hardware_shop.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemDTO(

        @NotNull(message = "Product ID cannot be null")
        Long productId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @NotNull(message = "Unit price cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Product's price must be greater than 0")
        BigDecimal unitPrice
) {
}