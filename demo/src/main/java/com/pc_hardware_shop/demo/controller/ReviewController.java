package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.ReviewDTO;
import com.pc_hardware_shop.demo.entity.Review;
import com.pc_hardware_shop.demo.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public Review createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return reviewService.createReview(reviewDTO);
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("by-id/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("by-product-id/{id}")
    public List<Review> getReviewsByProductId(@PathVariable Long id) {
        return reviewService.getReviewsByProductId(id);
    }

    @GetMapping("by-customer-id/{id}")
    public List<Review> getReviewsByCustomerId(@PathVariable Long id) {
        return reviewService.getReviewsByCustomerId(id);
    }
}
