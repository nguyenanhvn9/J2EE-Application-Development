// src/main/java/com/example/demo/service/ReviewService.java
package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository; // Cần để kiểm tra lịch sử mua hàng

    @Autowired
    private ProductService productService; // Cần để lấy Product

    /**
     * Gửi đánh giá mới cho sản phẩm.
     * Kiểm tra:
     * 1. Người dùng đã mua sản phẩm này chưa.
     * 2. Người dùng đã đánh giá sản phẩm này trước đó chưa.
     */
    @Transactional
    public Review submitReview(User user, Long productId, Integer rating, String comment) {
        if (user == null || productId == null || rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Dữ liệu đánh giá không hợp lệ.");
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại.");
        }

        // 1. Kiểm tra xem người dùng đã mua sản phẩm này chưa
        boolean hasPurchased = orderRepository.findByUserOrderByOrderDateDesc(user) // Lấy tất cả đơn hàng của user
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED) // Chỉ xem xét đơn hàng đã giao
                .flatMap(order -> order.getOrderItems().stream()) // Lấy tất cả OrderItem từ các đơn hàng
                .anyMatch(orderItem -> orderItem.getProduct().getId().equals(productId)); // Kiểm tra xem có ProductId này không

        if (!hasPurchased) {
            throw new RuntimeException("Bạn phải mua sản phẩm này và đơn hàng phải được giao để có thể đánh giá.");
        }

        // 2. Kiểm tra xem người dùng đã đánh giá sản phẩm này trước đó chưa
        Optional<Review> existingReview = reviewRepository.findByUserAndProduct(user, product);
        if (existingReview.isPresent()) {
            throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi.");
        }

        Review review = new Review(user, product, rating, comment);
        Review savedReview = reviewRepository.save(review);

        // Sau khi lưu đánh giá, bạn có thể muốn cập nhật lại điểm trung bình của sản phẩm
        // Mặc dù chúng ta tính nó trong getter của Product, việc này sẽ đảm bảo dữ liệu được tải lại chính xác
        product.getReviews().add(savedReview); // Thêm vào set để tính AverageRating nếu product đang trong session

        return savedReview;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsForProduct(Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại.");
        }
        // Force fetch reviews if Product entity uses LAZY
        // product.getReviews().size();
        return reviewRepository.findByProductOrderByCreatedAtDesc(product);
    }

    @Transactional(readOnly = true)
    public Optional<Review> getUserReviewForProduct(User user, Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return Optional.empty(); // Hoặc throw exception nếu sản phẩm không tồn tại
        }
        return reviewRepository.findByUserAndProduct(user, product);
    }

    /**
     * Kiểm tra xem người dùng đã mua sản phẩm chưa
     * @param user Người dùng
     * @param productId ID sản phẩm
     * @return true nếu đã mua và đơn hàng đã giao, ngược lại false
     */
    @Transactional(readOnly = true)
    public boolean hasUserPurchasedProduct(User user, Long productId) {
        if (user == null || productId == null) {
            return false;
        }

        return orderRepository.findByUserOrderByOrderDateDesc(user)
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getOrderItems().stream())
                .anyMatch(orderItem -> orderItem.getProduct().getId().equals(productId));
    }
}