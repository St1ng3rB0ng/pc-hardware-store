package com.pc_hardware_shop.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses", indexes = {@Index(columnList = "customer_id")})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "city", nullable = false, length = 100)
    private String city;
    @Column(name = "street", nullable = false, length = 200)
    private String street;
    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;
}
