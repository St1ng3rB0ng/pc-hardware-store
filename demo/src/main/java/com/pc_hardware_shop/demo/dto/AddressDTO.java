package com.pc_hardware_shop.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        @NotNull(message = "Customer ID cannot be null")
        Long customerId,

        @NotBlank(message = "Field city cannot be blank")
        @Size(min = 1, max = 100, message = "City name must be between 1 and 100 characters")
        String city,

        @NotBlank(message = "Field street cannot be blank")
        @Size(min = 1, max = 200, message = "Street name must be between 1 and 200 characters")
        String street,

        @NotBlank(message = "Field postal code cannot be blank")
        @Size(min = 1, max = 20, message = "Postal code must be between 1 and 20 characters")
        String postalCode) {
}
