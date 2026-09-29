package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.OrderDTO;
import com.pc_hardware_shop.demo.entity.Order;
import com.pc_hardware_shop.demo.service.OrderService;
import com.pc_hardware_shop.demo.staticData.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<List<Order>> getOrdersByShippingAddressId(@PathVariable Long addressId) {
        List<Order> orders = orderService.getOrdersByShippingAddressId(addressId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
}
