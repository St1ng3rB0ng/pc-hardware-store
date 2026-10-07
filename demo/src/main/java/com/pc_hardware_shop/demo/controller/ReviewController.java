package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.ReviewDTO;
import com.pc_hardware_shop.demo.entity.Review;
import com.pc_hardware_shop.demo.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long customerId) {

        if (productId != null) {
            return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
        }
        if (customerId != null) {
            return ResponseEntity.ok(reviewService.getReviewsByCustomerId(customerId));
        }

        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        Review createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }
}