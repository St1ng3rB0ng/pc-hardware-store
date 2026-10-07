package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.ReviewResponseDTO;
import com.pc_hardware_shop.demo.entity.ReviewResponse;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.ReviewRepository;
import com.pc_hardware_shop.demo.repository.ReviewResponseRepository;
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
public class ReviewResponseService {

    private final ReviewResponseRepository reviewResponseRepository;
    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getAllReviewResponses() {
        return reviewResponseRepository.findAll();
    }

    public ReviewResponse getReviewResponseById(Long id) {
        return reviewResponseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review response with id '" + id + "' not found"));
    }

    public List<ReviewResponse> getReviewResponsesByReviewId(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new NotFoundException("Review with id '" + reviewId + "' not found");
        }
        return reviewResponseRepository.findByReviewId(reviewId);
    }

    @Transactional
    public ReviewResponse createReviewResponse(ReviewResponseDTO reviewResponseDTO) {
        if (!reviewRepository.existsById(reviewResponseDTO.reviewId())) {
            throw new NotFoundException("Review with id '" + reviewResponseDTO.reviewId() + "' not found");
        }

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .reviewId(reviewResponseDTO.reviewId())
                .managerName(reviewResponseDTO.managerName() != null ? reviewResponseDTO.managerName().trim() : null)
                .responseText(reviewResponseDTO.responseText().trim())
                .createdAt(Instant.now())
                .build();

        ReviewResponse savedResponse = reviewResponseRepository.save(reviewResponse);
        log.info("Successfully created review response with ID: {} for review ID: {}",
                savedResponse.getId(), savedResponse.getReviewId());

        return savedResponse;
    }
}