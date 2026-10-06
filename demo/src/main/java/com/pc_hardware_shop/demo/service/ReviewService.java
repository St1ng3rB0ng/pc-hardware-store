package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.ReviewDTO;
import com.pc_hardware_shop.demo.entity.Review;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.CustomerRepository;
import com.pc_hardware_shop.demo.repository.ProductRepository;
import com.pc_hardware_shop.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Review createReview(ReviewDTO reviewDTO) {
        if (!customerRepository.existsById(reviewDTO.customerId())) {
            throw new NotFoundException("Customer with id '" + reviewDTO.customerId() + "' not found");
        }
        if (!productRepository.existsById(reviewDTO.productId())) {
            throw new NotFoundException("Product with id '" + reviewDTO.productId() + "' not found");
        }
        Review review = Review.builder()
                .customerId(reviewDTO.customerId())
                .productId(reviewDTO.productId())
                .message(reviewDTO.message())
                .rating(reviewDTO.rating())
                .createdAt(Instant.now())
                .build();
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id '" + id + "' not found"));
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByCustomerId(Long customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }
}
