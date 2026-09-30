package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.CartItem;
import com.pc_hardware_shop.demo.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    List<CartItem> findByIdCartId(Long cartId);

    void deleteByIdCartId(Long cartId);
}