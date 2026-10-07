package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.ReviewDTO;
import com.pc_hardware_shop.demo.entity.Review;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.CustomerRepository;
import com.pc_hardware_shop.demo.repository.ProductRepository;
import com.pc_hardware_shop.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id '" + id + "' not found"));
    }

    public List<Review> getReviewsByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id '" + productId + "' not found");
        }
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer with id '" + customerId + "' not found");
        }
        return reviewRepository.findByCustomerId(customerId);
    }

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
                .message(reviewDTO.message() != null ? reviewDTO.message().trim() : null)
                .rating(reviewDTO.rating())
                .createdAt(Instant.now())
                .build();

        Review savedReview = reviewRepository.save(review);
        log.info("Successfully created review with ID: {} for product ID: {} by customer ID: {}",
                savedReview.getId(), savedReview.getProductId(), savedReview.getCustomerId());

        return savedReview;
    }
}