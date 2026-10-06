package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.entity.Payment;
import com.pc_hardware_shop.demo.service.PaymentService;
import com.pc_hardware_shop.demo.staticData.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/order-id/{orderId}/amount/{amount}")
    public ResponseEntity<Payment> createPayment(@PathVariable Long orderId, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(paymentService.createPayment(orderId, amount));
    }

    @PutMapping("payment-id/{paymentId}/status")
    public ResponseEntity<Payment> updateStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updateStatus(paymentId, status));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("order-id/{orderId}")
    public ResponseEntity<List<Payment>> findByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.findByOrderId(orderId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Payment>> findAllByStatus(@RequestParam PaymentStatus status) {
        return ResponseEntity.ok(paymentService.findAllByStatus(status));
    }

    @GetMapping("payment-id/{paymentId}")
    public ResponseEntity<Payment> findById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.findById(paymentId).orElse(null));
    }
}
