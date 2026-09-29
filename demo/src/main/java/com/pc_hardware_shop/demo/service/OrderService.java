package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.OrderDTO;
import com.pc_hardware_shop.demo.entity.Order;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.AddressRepository;
import com.pc_hardware_shop.demo.repository.CustomerRepository;
import com.pc_hardware_shop.demo.repository.OrderRepository;
import com.pc_hardware_shop.demo.staticData.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Order createOrder(OrderDTO order) {
        if (!customerRepository.existsById(order.customerId())) {
            throw new IllegalArgumentException("Customer with id '" + order.customerId() + "' does not exist");
        }
        if (!addressRepository.existsById(order.shippingAddressId())) {
            throw new IllegalArgumentException("Shipping address with id '" + order.shippingAddressId() + "' does not exist");
        }
        Order createdOrder = Order.builder()
                .customerId(order.customerId())
                .shippingAddressId(order.shippingAddressId())
                .status(order.status() != null ? order.status() : OrderStatus.CREATED)
                .createdAt(Instant.now())
                .build();

        return orderRepository.save(createdOrder);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        return order;
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order with id '" + orderId + "' not found"));
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByShippingAddressId(Long shippingAddressId) {
        return orderRepository.findByShippingAddressId(shippingAddressId);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatus(orderStatus);
    }
}