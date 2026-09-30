package com.pc_hardware_shop.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        Long cartId,
        List<CartItemResponseDTO> items,
        BigDecimal totalPrice
) {}