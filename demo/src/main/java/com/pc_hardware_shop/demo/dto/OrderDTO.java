package com.pc_hardware_shop.demo.dto;

import com.pc_hardware_shop.demo.staticData.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderDTO(
        @NotNull(message = "Customer ID cannot be null")
        Long customerId,
        @NotNull(message = "Shipping Address ID cannot be null")
        Long shippingAddressId,
        @NotNull(message = "Status cannot be null")
        OrderStatus status) {
}
