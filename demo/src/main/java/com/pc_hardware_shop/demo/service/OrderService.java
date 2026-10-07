package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.OrderDTO;
import com.pc_hardware_shop.demo.dto.OrderItemDTO;
import com.pc_hardware_shop.demo.entity.*;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.*;
import com.pc_hardware_shop.demo.staticData.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id '" + orderId + "' not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByShippingAddressId(Long shippingAddressId) {
        return orderRepository.findByShippingAddressId(shippingAddressId);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findByStatus(orderStatus);
    }

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
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer with id '" + customerId + "' not found");
        }
        if (!addressRepository.existsById(shippingAddressId)) {
            throw new NotFoundException("Shipping address with id '" + shippingAddressId + "' not found");
        }

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("Cart for customer id '" + customerId + "' is empty or does not exist"));

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
                throw new IllegalStateException("Not enough stock for product: " + product.getName()
                        + ". Requested: " + cartItem.getQuantity() + ", available: " + product.getStockQuantity());
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

            return OrderItem.builder()
                    .id(new OrderItemId(savedOrder.getOrderId(), product.getId()))
                    .quantity(cartItem.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
        }).toList();

        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cart.getItems());

        log.info("Successfully checked out order ID: {} for customer ID: {}", savedOrder.getOrderId(), customerId);
        return savedOrder;
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        if (!customerRepository.existsById(orderDTO.customerId())) {
            throw new NotFoundException("Customer with id '" + orderDTO.customerId() + "' not found");
        }
        if (!addressRepository.existsById(orderDTO.shippingAddressId())) {
            throw new NotFoundException("Shipping address with id '" + orderDTO.shippingAddressId() + "' not found");
        }

        Order order = Order.builder()
                .customerId(orderDTO.customerId())
                .shippingAddressId(orderDTO.shippingAddressId())
                .status(orderDTO.status() != null ? orderDTO.status() : OrderStatus.CREATED)
                .createdAt(Instant.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Successfully created order with ID: {} for customer ID: {}", savedOrder.getOrderId(), savedOrder.getCustomerId());

        return savedOrder;
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);

        log.info("Successfully updated status for order ID: {} to '{}'", orderId, newStatus);
        return order;
    }
}