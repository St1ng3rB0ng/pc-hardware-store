package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    void deleteBySku(String sku);

    void deleteByName(String name);

    boolean existsBySku(String sku);

    Optional<Product> findByName(String name);

    Optional<Product> findBySku(String sku);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByIsActive(boolean isActive);
}
