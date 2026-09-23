package com.pc_hardware_shop.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @NotBlank(message = "Product's name cannot be blank")
    @Size(min = 1, max = 200, message = "Product's name must be between 1 and 200 characters")
    private String name;

    @NotBlank(message = "Product's SKU cannot be blank")
    @Size(min = 1, max = 40, message = "Product's SKU must be between 1 and 40 characters")
    private String sku;

    @NotNull(message = "Product's price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product's price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Product's stock quantity cannot be null")
    @Min(value = 0, message = "Product's stock quantity cannot be negative")
    private Integer stockQuantity;

    private boolean isActive = true;
}