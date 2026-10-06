package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.*;
import com.pc_hardware_shop.demo.entity.*;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Cart getOrCreateCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .customerId(customerId)
                                .updatedAt(Instant.now())
                                .build()
                ));
    }

    @Transactional
    public CartResponseDTO addOrUpdateItem(Long customerId, AddToCartRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Cart cart = getOrCreateCart(customerId);
        CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElse(CartItem.builder()
                        .id(cartItemId)
                        .cart(cart)
                        .product(product)
                        .quantity(0)
                        .build());

        cartItem.setQuantity(cartItem.getQuantity() + dto.quantity());
        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(Instant.now());
        return getCartDTO(customerId);
    }

    @Transactional
    public void removeItem(Long customerId, Long productId) {
        Cart cart = getOrCreateCart(customerId);
        cartItemRepository.deleteById(new CartItemId(cart.getId(), productId));
    }

    @Transactional
    public CartResponseDTO getCartDTO(Long customerId) {
        Cart cart = getOrCreateCart(customerId);

        List<CartItemResponseDTO> items = cart.getItems().stream().map(item -> {
            BigDecimal price = item.getProduct().getPrice();
            BigDecimal subTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
            return new CartItemResponseDTO(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    price,
                    item.getQuantity(),
                    subTotal
            );
        }).toList();

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponseDTO::subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponseDTO(cart.getId(), items, totalPrice);
    }
}