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
import com.techshop.service.UserService;

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

    @Autowired
    private UserService userService;

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model, Authentication authentication,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "success", required = false) String success) {
        ControllerUtils.addUsername(model);
        Product product = productService.getProduct(id);
        if (product == null)
            return "redirect:/";
        model.addAttribute("product", product);
        List<Review> reviews = reviewService.getReviewsByProduct(id);
        if (reviews == null)
            reviews = java.util.Collections.emptyList();
        System.out.println("Reviews for product " + id + ": " + reviews.size());
        model.addAttribute("reviews", reviews);
        double averageRating = 0;
        if (!reviews.isEmpty()) {
            averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        }
        model.addAttribute("averageRating", averageRating);
        Long userId = null;
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            com.techshop.model.User user = (com.techshop.model.User) authentication.getPrincipal();
            userId = user.getId();
            username = user.getUsername();
        }
        boolean canReview = false;
        if (userId != null && orderService.hasUserPurchasedProduct(userId, id)
                && !reviewService.hasUserReviewed(id, userId)) {
            canReview = true;
        }
        model.addAttribute("canReview", canReview);
        model.addAttribute("username", username);
        if (error != null)
            model.addAttribute("error", error);
        if (success != null)
            model.addAttribute("success", success);
        return "product_detail";
    }

    @GetMapping("/review-form")
    public String reviewForm(@RequestParam Long productId, Model model, Authentication authentication) {
        Product product = productService.getProduct(productId);
        boolean canReview = false;
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            String usernameSpring = authentication.getName();
            com.techshop.model.User user = userService.findByUsername(usernameSpring);
            if (user != null) {
                username = user.getUsername();
                Long userId = user.getId();
                if (userId != null && product != null
                        && orderService.hasUserPurchasedProduct(userId, productId)
                        && !reviewService.hasUserReviewed(productId, userId)) {
                    canReview = true;
                }
            }
        }
        model.addAttribute("product", product);
        model.addAttribute("canReview", canReview);
        model.addAttribute("username", username);
        if (product == null) {
            model.addAttribute("error", "Sản phẩm không tồn tại hoặc đã bị xóa!");
        }
        return "review_form";
    }
}