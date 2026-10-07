package com.pc_hardware_shop.demo.entity;

import com.pc_hardware_shop.demo.staticData.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;
    @Column(nullable = false, unique = true, length = 254)
    private String email;
    @Column(name = "password_hash", nullable = false, unique = true, length = 255)
    String passwordHash;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    UserRole role;
    @Column(name = "created_at")
    Instant createdAt;
}
