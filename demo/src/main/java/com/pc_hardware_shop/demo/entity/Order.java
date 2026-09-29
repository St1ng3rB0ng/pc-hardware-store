package com.pc_hardware_shop.demo.entity;

import com.pc_hardware_shop.demo.staticData.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_customer", columnList = "customer_id"),
        @Index(name = "idx_orders_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "shipping_address_id", nullable = false)
    private Long shippingAddressId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
