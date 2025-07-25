package com.techshop.service;

import com.techshop.model.Review;
import com.techshop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProduct_Id(productId);
    }

    public boolean hasUserReviewed(Long productId, Long userId) {
        return reviewRepository.existsByProductIdAndUserId(productId, userId);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
}