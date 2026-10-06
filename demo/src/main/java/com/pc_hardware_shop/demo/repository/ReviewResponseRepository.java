package com.pc_hardware_shop.demo.repository;

import com.pc_hardware_shop.demo.entity.ReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewResponseRepository extends JpaRepository<ReviewResponse, Long> {
    Optional<ReviewResponse> findById(Long id);
    List<ReviewResponse> findByReviewId(Long reviewId);

    boolean existsByReviewId(Long reviewId);
}
