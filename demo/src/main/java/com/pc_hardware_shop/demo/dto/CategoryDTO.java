package com.pc_hardware_shop.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CategoryDTO(

        @NotNull(message = "Category's name can not be null")

        @NotBlank(message = "Category's name can not be blank")

        @Size(max = 255, message = "Category's name can not be longer 255 characters")

        @Size(min = 1, message = "Category's name must contain at least 1 character")

        String name

) {}