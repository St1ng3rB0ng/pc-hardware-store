package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.OrderDTO;
import com.pc_hardware_shop.demo.dto.OrderItemDTO;
import com.pc_hardware_shop.demo.entity.Order;
import com.pc_hardware_shop.demo.service.OrderService;
import com.pc_hardware_shop.demo.staticData.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long shippingAddressId,
            @RequestParam(required = false) OrderStatus status) {

        if (customerId != null) {
            return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
        }
        if (shippingAddressId != null) {
            return ResponseEntity.ok(orderService.getOrdersByShippingAddressId(shippingAddressId));
        }
        if (status != null) {
            return ResponseEntity.ok(orderService.getOrdersByStatus(status));
        }

        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderItemsByOrderId(orderId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            @RequestParam Long customerId,
            @RequestParam Long shippingAddressId) {
        Order createdOrder = orderService.checkout(customerId, shippingAddressId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}