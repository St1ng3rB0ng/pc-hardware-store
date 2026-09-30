package com.pc_hardware_shop.demo.dto;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long productId,
        String productName,
        BigDecimal currentPrice,
        Integer quantity,
        BigDecimal subTotal // currentPrice * quantity
) {}
