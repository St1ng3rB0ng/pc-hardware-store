package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.Order;
import com.pc_hardware_shop.demo.staticData.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByShippingAddressId(Long shippingAddressId);
}
