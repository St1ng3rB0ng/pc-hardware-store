package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.ReviewResponseDTO;
import com.pc_hardware_shop.demo.entity.ReviewResponse;
import com.pc_hardware_shop.demo.service.ReviewResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-responses")
@RequiredArgsConstructor
public class ReviewResponseController {

    private final ReviewResponseService reviewResponseService;

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviewResponses(
            @RequestParam(required = false) Long reviewId) {

        if (reviewId != null) {
            return ResponseEntity.ok(reviewResponseService.getReviewResponsesByReviewId(reviewId));
        }

        return ResponseEntity.ok(reviewResponseService.getAllReviewResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewResponseService.getReviewResponseById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReviewResponse(
            @Valid @RequestBody ReviewResponseDTO reviewResponseDTO) {
        ReviewResponse createdResponse = reviewResponseService.createReviewResponse(reviewResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponse);
    }
}