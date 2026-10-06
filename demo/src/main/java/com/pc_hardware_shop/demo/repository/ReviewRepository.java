package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long id);
    List<Review> findByProductId(Long productId);
    List<Review> findByCustomerId(Long customerId);

    boolean existsByProductId(Long productId);
    boolean existsByCustomerId(Long customerId);
}
