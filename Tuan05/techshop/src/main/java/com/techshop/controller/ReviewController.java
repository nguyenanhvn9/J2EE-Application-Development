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
        boolean hasPurchased = orderService.hasUserPurchasedProduct(userId, id);
        if (!hasPurchased) {
            model.addAttribute("error", "Bạn chỉ có thể đánh giá sản phẩm đã mua.");
            return "redirect:/products/product/" + id + "?error=notpurchased";
        }
        // Kiểm tra đã đánh giá chưa
        if (reviewService.hasUserReviewed(id, userId)) {
            model.addAttribute("error", "Bạn chỉ được đánh giá một lần cho mỗi sản phẩm.");
            return "redirect:/products/product/" + id + "?error=alreadyreviewed";
        }
        try {
            Review review = new Review();
            review.setProduct(productService.getProduct(id));
            review.setUserId(userId);
            review.setUsername(username);
            review.setRating(rating);
            review.setComment(comment);
            reviewService.saveReview(review);
            return "redirect:/products/product/" + id + "?success=reviewed";
        } catch (Exception e) {
            logger.error("Lỗi khi lưu đánh giá sản phẩm", e);
            return "redirect:/products/product/" + id + "?error=internal";
        }
    }
}