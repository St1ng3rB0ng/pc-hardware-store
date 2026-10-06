package com.pc_hardware_shop.demo.dto;

import jakarta.validation.constraints.NotNull;

public record ReviewDTO(
        @NotNull(message = "Customer ID is required")
        Long customerId,
        @NotNull(message = "Product ID is required")
        Long productId,
        String message,
        @NotNull(message = "Rating is required")
        Byte rating
) {
}
