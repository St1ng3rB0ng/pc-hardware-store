package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.entity.Payment;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.OrderRepository;
import com.pc_hardware_shop.demo.repository.PaymentRepository;
import com.pc_hardware_shop.demo.staticData.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

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

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment updateStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment with id '" + paymentId + "' not found"));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<Payment> findAllByStatus(PaymentStatus status) {
        return paymentRepository.findAllByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Payment> findByOrderId(Long orderId) {
        if (!paymentRepository.existsByOrderId(orderId)) {
            throw new NotFoundException("Order with id '" + orderId + "' not found");
        }
        return paymentRepository.findByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
