package com.techshop.controller;

import com.techshop.model.Review;
import com.techshop.service.ReviewService;
import com.techshop.service.ProductService;
import com.techshop.service.OrderService;
import com.techshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.techshop.model.Product;

import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("/product/{id}/review")
    public String submitReview(@PathVariable Long id,
            @RequestParam int rating,
            @RequestParam String comment,
            Authentication authentication,
            Model model) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                model.addAttribute("error", "Bạn cần đăng nhập để đánh giá.");
                return "redirect:/login";
            }
            String username = authentication.getName();
            com.techshop.model.User user = userService.findByUsername(username);
            if (user == null) {
                model.addAttribute("error", "Tài khoản không hợp lệ.");
                return "redirect:/login";
            }
            Long userId = user.getId();
            // Kiểm tra đã mua hàng chưa
            boolean hasPurchased = false;
            try {
                hasPurchased = orderService.hasUserPurchasedProduct(userId, id);
            } catch (Exception e) {
                logger.error("Lỗi khi kiểm tra mua hàng: userId={}, productId={}", userId, id, e);
            }
            if (!hasPurchased) {
                model.addAttribute("error", "Bạn chỉ có thể đánh giá sản phẩm đã mua.");
                return "redirect:/product/" + id + "?error=notpurchased";
            }
            // Kiểm tra đã đánh giá chưa
            boolean alreadyReviewed = false;
            try {
                alreadyReviewed = reviewService.hasUserReviewed(id, userId);
            } catch (Exception e) {
                logger.error("Lỗi khi kiểm tra đã đánh giá: userId={}, productId={}", userId, id, e);
            }
            if (alreadyReviewed) {
                model.addAttribute("error", "Bạn chỉ được đánh giá một lần cho mỗi sản phẩm.");
                return "redirect:/product/" + id + "?error=alreadyreviewed";
            }
            Product product = productService.getProduct(id);
            if (product == null) {
                model.addAttribute("error", "Sản phẩm không tồn tại hoặc đã bị xóa!");
                return "redirect:/profile?tab=orders";
            }
            if (userId == null || username == null || rating < 1 || rating > 5 || comment == null) {
                logger.error("Thiếu thông tin khi lưu đánh giá: userId={}, username={}, rating={}, comment={}", userId,
                        username, rating, comment);
                model.addAttribute("error", "Thiếu thông tin đánh giá.");
                return "redirect:/product/" + id + "?error=invaliddata";
            }
            Review review = new Review();
            review.setProduct(product);
            review.setUserId(userId);
            review.setUsername(username);
            review.setRating(rating);
            review.setComment(comment);
            reviewService.saveReview(review);
            logger.info("Lưu đánh giá thành công: userId={}, username={}, productId={}, rating={}", userId, username,
                    id, rating);
            return "redirect:/profile?tab=orders&success=reviewed";
        } catch (Exception e) {
            logger.error("Lỗi khi lưu đánh giá sản phẩm (toàn cục)", e);
            return "redirect:/profile?tab=orders&error=internal";
        }
    }

    // API trả về danh sách review dạng JSON cho sản phẩm
    @GetMapping("/product/{id}/reviews")
    @ResponseBody
    public List<Review> getReviews(@PathVariable Long id) {
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        if (reviews == null)
            reviews = java.util.Collections.emptyList();
        return reviews;
    }
}