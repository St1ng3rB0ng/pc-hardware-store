package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByFullName(String name);

    boolean existsByEmail(String email);

    Optional<Customer> findByFullName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteById(Long id);

    void deleteByEmail(String email);

    void deleteByFullName(String name);

}
