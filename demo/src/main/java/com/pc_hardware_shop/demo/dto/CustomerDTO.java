package com.pc_hardware_shop.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerDTO(
        @NotBlank(message = "Full name cannot be blank")
        @Size(min = 1, max = 150, message = "Full name must be between 1 and 150 characters")
        String fullName,
        @Email(message = "Provided email is not valid")
        @NotBlank(message = "Email cannot be blank")
        @Size(min = 1, max = 254, message = "Email must be between 1 and 254 characters")
        String email

) {}
