package com.pc_hardware_shop.demo.dto;

import com.pc_hardware_shop.demo.staticData.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @Email(message = "Provided email is not valid")
        @NotBlank(message = "Email cannot be blank")
        @Size(min = 1, max = 255, message = "Email must be between 1 and 255 characters")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
        String password,
        @NotNull(message = "Role is required")
        UserRole role
) {
}
