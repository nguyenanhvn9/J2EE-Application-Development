package com.techshop.controller;

import com.techshop.model.Product;
import com.techshop.model.Review;
import com.techshop.service.OrderService;
import com.techshop.service.ProductService;
import com.techshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.springframework.security.core.Authentication;
import com.techshop.util.ControllerUtils;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public ProductController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model, Authentication authentication,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "success", required = false) String success) {
        ControllerUtils.addUsername(model);
        Product product = productService.getProduct(id);
        if (product == null)
            return "redirect:/";
        model.addAttribute("product", product);
        // Lấy danh sách đánh giá
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        if (reviews == null)
            reviews = java.util.Collections.emptyList();
        model.addAttribute("reviews", reviews);
        // Tính điểm trung bình
        double averageRating = reviews.isEmpty() ? 0 : reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        model.addAttribute("averageRating", String.format("%.1f", averageRating));
        // Kiểm tra quyền gửi đánh giá
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            com.techshop.model.User user = (com.techshop.model.User) authentication.getPrincipal();
            userId = user.getId();
        }
        boolean canReview = false;
        if (userId != null && orderService.hasUserPurchasedProduct(userId, id)
                && !reviewService.hasUserReviewed(id, userId)) {
            canReview = true;
        }
        model.addAttribute("canReview", canReview);
        if (error != null)
            model.addAttribute("error", error);
        if (success != null)
            model.addAttribute("success", success);
        return "product_detail";
    }
}