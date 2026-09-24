package com.pc_hardware_shop.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false, unique = true, length = 254)
    private String email;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
