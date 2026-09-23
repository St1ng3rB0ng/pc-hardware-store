package com.pc_hardware_shop.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CategoryDTO(

        @NotBlank(message = "Category's name can not be blank")
        @Size(min = 1, max = 255, message = "Category's name must be between 1 and 255 characters")
        String name
) {}