package com.pc_hardware_shop.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewResponseDTO (
        @NotNull(message = "Review ID is required")
        Long reviewId,
        @NotBlank(message = "Manager name is required")
        String managerName,
        @NotBlank(message = "Response text is required")
        String responseText
){
}
