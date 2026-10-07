package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.entity.Payment;
import com.pc_hardware_shop.demo.service.PaymentService;
import com.pc_hardware_shop.demo.staticData.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) PaymentStatus status) {

        if (orderId != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByOrderId(orderId));
        }
        if (status != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
        }

        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam Long orderId,
            @RequestParam BigDecimal amount) {
        Payment createdPayment = paymentService.createPayment(orderId, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Payment> updateStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        Payment updatedPayment = paymentService.updateStatus(id, status);
        return ResponseEntity.ok(updatedPayment);
    }
}