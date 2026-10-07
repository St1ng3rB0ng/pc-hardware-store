package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.entity.Payment;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.OrderRepository;
import com.pc_hardware_shop.demo.repository.PaymentRepository;
import com.pc_hardware_shop.demo.staticData.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment with id '" + paymentId + "' not found"));
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order with id '" + orderId + "' not found");
        }
        return paymentRepository.findByOrderId(orderId);
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findAllByStatus(status);
    }

    /**
     * @param amount stands for final sum of order
     */
    @Transactional
    public Payment createPayment(Long orderId, BigDecimal amount) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order with id '" + orderId + "' not found");
        }

        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Successfully created payment with ID: {} for order ID: {}", savedPayment.getId(), orderId);

        return savedPayment;
    }

    @Transactional
    public Payment updateStatus(Long paymentId, PaymentStatus status) {
        Payment payment = getPaymentById(paymentId);
        payment.setStatus(status);

        log.info("Successfully updated status for payment ID: {} to '{}'", paymentId, status);
        return payment;
    }
}