package com.pc_hardware_shop.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "reviews", indexes = {@Index(name = "idx_review_customers", columnList = "customer_id"), @Index(name = "idx_review_products", columnList = "product_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "rating", nullable = false)
    private Byte rating;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
