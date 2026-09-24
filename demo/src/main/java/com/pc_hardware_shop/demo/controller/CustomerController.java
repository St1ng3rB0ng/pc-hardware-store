package com.pc_hardware_shop.demo.controller;


import com.pc_hardware_shop.demo.dto.CustomerDTO;
import com.pc_hardware_shop.demo.entity.Customer;
import com.pc_hardware_shop.demo.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/fullName")
    public ResponseEntity<Customer> getCustomerByFullName(@RequestParam String fullName) {
        Customer customer = customerService.getCustomerByFullName(fullName);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/email")
    public ResponseEntity<Void> deleteCustomerByEmail(@RequestParam String email) {
        customerService.deleteCustomerByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/fullName")
    public ResponseEntity<Void> deleteCustomerByFullName(@RequestParam String fullName) {
        customerService.deleteCustomerByFullName(fullName);
        return ResponseEntity.noContent().build();
    }
}
