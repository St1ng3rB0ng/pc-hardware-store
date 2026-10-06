package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.OrderDTO;
import com.pc_hardware_shop.demo.dto.OrderItemDTO;
import com.pc_hardware_shop.demo.entity.*;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.*;
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
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order with id '" + orderId + "' not found");
        }

        List<OrderItem> orderItems = orderItemRepository.findByIdOrderId(orderId);

        return orderItems.stream()
                .map(item -> new OrderItemDTO(
                        item.getId().getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice()
                ))
                .toList();
    }

    @Transactional
    public Order checkout(Long customerId, Long shippingAddressId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("Cart is empty"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot place an order with an empty cart");
        }

        Order order = Order.builder()
                .customerId(customerId)
                .shippingAddressId(shippingAddressId)
                .status(OrderStatus.CREATED)
                .createdAt(Instant.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }

            return OrderItem.builder()
                    .id(new OrderItemId(savedOrder.getOrderId(), product.getId()))
                    .quantity(cartItem.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
        }).toList();

        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(cart.getItems());

        return savedOrder;
    }

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