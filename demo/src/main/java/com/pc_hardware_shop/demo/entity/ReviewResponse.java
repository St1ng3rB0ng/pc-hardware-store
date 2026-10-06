package com.pc_hardware_shop.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "review_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id", nullable = false)
    private Long id;
    @Column(name = "review_id", nullable = false)
    private Long reviewId;
    @Column(name = "manager_name", nullable = false)
    private String managerName;
    @Column(name = "response_text", nullable = false)
    private String responseText;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
