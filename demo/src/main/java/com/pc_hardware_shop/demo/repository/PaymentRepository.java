package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.Payment;
import com.pc_hardware_shop.demo.staticData.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(Long id);

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findAllByStatus(PaymentStatus status);

    boolean existsByOrderId(Long orderId);
}
