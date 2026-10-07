package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.AddToCartRequestDTO;
import com.pc_hardware_shop.demo.dto.CartItemResponseDTO;
import com.pc_hardware_shop.demo.dto.CartResponseDTO;
import com.pc_hardware_shop.demo.entity.Cart;
import com.pc_hardware_shop.demo.entity.CartItem;
import com.pc_hardware_shop.demo.entity.CartItemId;
import com.pc_hardware_shop.demo.entity.Product;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.CartItemRepository;
import com.pc_hardware_shop.demo.repository.CartRepository;
import com.pc_hardware_shop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
                .orElseThrow(() -> new NotFoundException("Product with id '" + dto.productId() + "' not found"));

        Cart cart = getOrCreateCart(customerId);
        CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseGet(() -> CartItem.builder()
                        .id(cartItemId)
                        .cart(cart)
                        .product(product)
                        .quantity(0)
                        .build());

        cartItem.setQuantity(cartItem.getQuantity() + dto.quantity());
        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(Instant.now());
        log.info("Successfully added/updated product ID: {} in cart for customer ID: {}. New quantity: {}",
                product.getId(), customerId, cartItem.getQuantity());

        return getCartDTO(customerId);
    }

    @Transactional
    public void removeItem(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Cart for customer id '" + customerId + "' not found"));

        CartItemId cartItemId = new CartItemId(cart.getId(), productId);
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new NotFoundException("Item with product id '" + productId + "' not found in cart");
        }

        cartItemRepository.deleteById(cartItemId);
        cart.setUpdatedAt(Instant.now());
        log.info("Successfully removed product ID: {} from cart for customer ID: {}", productId, customerId);
    }

    public CartResponseDTO getCartDTO(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> getOrCreateCart(customerId));

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