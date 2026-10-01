package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.OrderItem;
import com.pc_hardware_shop.demo.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

    List<OrderItem> findByIdProductId(Long productId);

    List<OrderItem> findByIdOrderId(Long orderId);

    void deleteByIdOrderId(Long orderId);

    boolean existsByIdProductId(Long productId);

}
