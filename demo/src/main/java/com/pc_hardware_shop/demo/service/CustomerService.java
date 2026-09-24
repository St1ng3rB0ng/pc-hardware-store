package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.CustomerDTO;
import com.pc_hardware_shop.demo.entity.Customer;
import com.pc_hardware_shop.demo.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer with ID '" + id + "' does not exist"));
    }

    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Customer with email '" + email + "' does not exist"));
    }

    @Transactional(readOnly = true)
    public Customer getCustomerByFullName(String fullName) {
        return customerRepository.findByFullName(fullName).orElseThrow(() -> new IllegalArgumentException("Customer with full name '" + fullName + "' does not exist"));
    }

    public Customer createCustomer(CustomerDTO customerDTO){
        if (customerRepository.existsByEmail(customerDTO.email())){
            throw new IllegalArgumentException("Customer with email '" + customerDTO.email() + "' already exists");
        }
        Customer customer = Customer.builder()
                .fullName(customerDTO.fullName())
                .email(customerDTO.email())
                .build();
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer with ID '" + id + "' does not exist");
        }
        customerRepository.deleteById(id);
    }

    public  void deleteCustomerByEmail(String email){
        if (!customerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Customer with email '" + email + "' does not exist");
        }
        customerRepository.deleteByEmail(email);
    }

    public void deleteCustomerByFullName(String fullName){
        if (!customerRepository.existsByFullName(fullName)) {
            throw new IllegalArgumentException("Customer with full name '" + fullName + "' does not exist");
        }
        customerRepository.deleteByFullName(fullName);
    }

}
