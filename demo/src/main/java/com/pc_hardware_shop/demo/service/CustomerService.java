package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.CustomerDTO;
import com.pc_hardware_shop.demo.entity.Customer;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with id '" + id + "' not found"));
    }

    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Customer with email '" + email + "' not found"));
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomersByFullName(String fullName) {
        return customerRepository.findByFullName(fullName);
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.email())) {
            throw new IllegalArgumentException("Customer with email '" + customerDTO.email() + "' already exists");
        }

        Customer customer = Customer.builder()
                .fullName(customerDTO.fullName())
                .email(customerDTO.email())
                .userId(customerDTO.userId())
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Successfully created new customer with ID: {} and email: {}",
                savedCustomer.getId(), savedCustomer.getEmail());

        return savedCustomer;
    }

    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Customer with id '" + id + "' not found");
        }
        customerRepository.deleteById(id);
        log.info("Successfully deleted customer with ID: {}", id);
    }

    public void deleteCustomerByEmail(String email) {
        if (!customerRepository.existsByEmail(email)) {
            throw new NotFoundException("Customer with email '" + email + "' not found");
        }
        customerRepository.deleteByEmail(email);
        log.info("Successfully deleted customer with email: {}", email);
    }

    public void deleteCustomerByFullName(String fullName) {
        if (!customerRepository.existsByFullName(fullName)) {
            throw new NotFoundException("Customers with full name '" + fullName + "' not found");
        }
        customerRepository.deleteByFullName(fullName);
        log.info("Successfully deleted customers with full name: {}", fullName);
    }
}