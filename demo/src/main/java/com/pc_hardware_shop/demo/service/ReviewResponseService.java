package com.pc_hardware_shop.demo.service;

import com.pc_hardware_shop.demo.dto.ReviewResponseDTO;
import com.pc_hardware_shop.demo.entity.ReviewResponse;
import com.pc_hardware_shop.demo.exceprion.NotFoundException;
import com.pc_hardware_shop.demo.repository.ReviewRepository;
import com.pc_hardware_shop.demo.repository.ReviewResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewResponseService {
    private final ReviewResponseRepository reviewResponseRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResponse createReviewResponse(ReviewResponseDTO reviewResponseDTO) {
        if (!reviewRepository.existsById(reviewResponseDTO.reviewId())) {
            throw new NotFoundException("Review with id '" + reviewResponseDTO.reviewId() + "' not found");
        }
        ReviewResponse reviewResponse = ReviewResponse.builder()
                .reviewId(reviewResponseDTO.reviewId())
                .managerName(reviewResponseDTO.managerName())
                .responseText(reviewResponseDTO.responseText())
                .createdAt(Instant.now())
                .build();
        return reviewResponseRepository.save(reviewResponse);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getAllReviewResponses() {
        return reviewResponseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewResponsesByReviewId(Long reviewId) {
        return reviewResponseRepository.findByReviewId(reviewId);
    }
}
