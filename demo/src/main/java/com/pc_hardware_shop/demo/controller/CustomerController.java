package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.CustomerDTO;
import com.pc_hardware_shop.demo.entity.Customer;
import com.pc_hardware_shop.demo.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getCustomers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String fullName) {

        if (email != null && !email.isBlank()) {
            return ResponseEntity.ok(customerService.getCustomerByEmail(email.trim()));
        }
        if (fullName != null && !fullName.isBlank()) {
            return ResponseEntity.ok(customerService.getCustomersByFullName(fullName.trim()));
        }

        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-email")
    public ResponseEntity<Void> deleteCustomerByEmail(@RequestParam String email) {
        customerService.deleteCustomerByEmail(email.trim());
        return ResponseEntity.noContent().build();
    }
}