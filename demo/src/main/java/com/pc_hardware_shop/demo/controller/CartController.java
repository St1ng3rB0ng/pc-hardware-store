package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.AddToCartRequestDTO;
import com.pc_hardware_shop.demo.dto.CartResponseDTO;
import com.pc_hardware_shop.demo.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartResponseDTO> getCartByCustomerId(@PathVariable Long customerId) {
        CartResponseDTO cart = cartService.getCartDTO(customerId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/customer/{customerId}/items")
    public ResponseEntity<CartResponseDTO> addItemToCart(
            @PathVariable Long customerId,
            @Valid @RequestBody AddToCartRequestDTO request) {
        CartResponseDTO updatedCart = cartService.addOrUpdateItem(customerId, request);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        cartService.removeItem(customerId, productId);
        return ResponseEntity.noContent().build();
    }
}