package com.pc_hardware_shop.demo.controller;

import com.pc_hardware_shop.demo.dto.ReviewResponseDTO;
import com.pc_hardware_shop.demo.entity.ReviewResponse;
import com.pc_hardware_shop.demo.service.ReviewResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-responses")
@RequiredArgsConstructor
public class ReviewResponseController {
    private final ReviewResponseService reviewResponseService;

    @PostMapping
    public ReviewResponse createReviewResponse(@Valid @RequestBody ReviewResponseDTO reviewResponseDTO) {
        return reviewResponseService.createReviewResponse(reviewResponseDTO);
    }

    @GetMapping
    public List<ReviewResponse> getAllReviewResponses() {
        return reviewResponseService.getAllReviewResponses();
    }

    @GetMapping("by-review-id/{id}")
    public List<ReviewResponse> getReviewResponsesByReviewId(@PathVariable Long id) {
        return reviewResponseService.getReviewResponsesByReviewId(id);
    }
}
