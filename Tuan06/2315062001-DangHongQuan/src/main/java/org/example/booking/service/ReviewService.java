package org.example.booking.service;

import org.example.booking.Response.OrderItemRepository;
import org.example.booking.Response.ReviewRepository;
import org.example.booking.model.Product;
import org.example.booking.model.Review;
import org.example.booking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public boolean hasUserPurchasedProduct(Long userId, Long productId) {
        return orderItemRepository.existsByOrderUserIdAndProductId(userId, productId);
    }

    public void submitReview(User user, Product product, int rating, String comment) {
        if (!hasUserPurchasedProduct(user.getId(), product.getId())) {
            throw new IllegalArgumentException("User must purchase before reviewing");
        }
        if (reviewRepository.existsByUserIdAndProductId(user.getId(), product.getId())) {
            throw new IllegalStateException("User already reviewed this product");
        }
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
    }
    public void save(Review review) {
        reviewRepository.save(review);
    }
    public boolean hasUserReviewedProduct(Long userId, Long productId) {
        return reviewRepository.existsByUserIdAndProductId(userId, productId);
    }
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}

