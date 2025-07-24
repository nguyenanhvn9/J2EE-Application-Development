package org.example.booking.controller;

import org.example.booking.dto.AddressDTO;
import org.example.booking.model.*;
import org.example.booking.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired private UserService userService;
    @Autowired private OrderService orderService;
    @Autowired private ProductService productService;
    @Autowired private ReviewService reviewService;
    @Autowired private AddressService addressService;

    @GetMapping
    public String viewProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByUsername(email);

        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        List<Order> orders = orderService.findDeliveredOrdersByUser(user.getId());
        List<Address> addresses = addressService.getAddressesByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("addresses", addresses);
        model.addAttribute("newAddress", new Address());

        return "profile/index";
    }

    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        return "profile/orderDetail";
    }

    @PostMapping("/review")
    public String submitReviewForm(@ModelAttribute Review review,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(userDetails.getUsername());
            Product product = productService.findById(review.getProduct().getId());

            if (user == null || product == null) {
                redirectAttributes.addFlashAttribute("error", "Người dùng hoặc sản phẩm không tồn tại");
                return "redirect:/profile";
            }

            boolean hasPurchased = orderService.hasUserPurchasedProduct(user.getId(), product.getId());
            if (!hasPurchased) {
                redirectAttributes.addFlashAttribute("error", "Bạn chỉ có thể đánh giá sản phẩm đã mua");
                return "redirect:/profile";
            }

            if (reviewService.hasUserReviewedProduct(user.getId(), product.getId())) {
                redirectAttributes.addFlashAttribute("error", "Bạn đã đánh giá sản phẩm này rồi");
                return "redirect:/profile";
            }

            review.setUser(user);
            review.setProduct(product);
            reviewService.save(review);

            redirectAttributes.addFlashAttribute("success", "Đánh giá đã được gửi thành công!");
        } catch (Exception e) {
            logger.error("Error saving review: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi gửi đánh giá");
        }
        return "redirect:/profile";
    }

    @PostMapping("/reviews/{productId}")
    public String submitReviewDirect(@PathVariable Long productId,
                                     @RequestParam int rating,
                                     @RequestParam String comment,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(userDetails.getUsername());
            Product product = productService.findById(productId);

            reviewService.submitReview(user, product, rating, comment);
            redirectAttributes.addFlashAttribute("success", "Đánh giá đã được gửi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/" + productId;
    }

    @GetMapping("/reviews")
    public String viewReviews(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("reviews", user.getReviews());
        return "profile/reviews";
    }

    // ----------- Address Methods ----------------

    @PostMapping("/address/add")
    public String saveOrUpdateAddress(@ModelAttribute("newAddress") Address address,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(userDetails.getUsername());

            if (address.getId() != null) {
                // Update existing address
                Address existing = addressService.getAddressById(address.getId());
                if (existing != null && existing.getUser().getId().equals(user.getId())) {
                    existing.setAddressLine(address.getAddressLine());
                    existing.setCity(address.getCity());
                    existing.setState(address.getState());
                    existing.setZipCode(address.getZipCode());
                    existing.setCountry(address.getCountry());
                    addressService.saveAddress(existing);
                    redirectAttributes.addFlashAttribute("success", "Đã cập nhật địa chỉ");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Không thể chỉnh sửa địa chỉ này");
                }
            } else {
                // Add new address
                address.setUser(user);
                addressService.saveAddress(address);
                redirectAttributes.addFlashAttribute("success", "Đã thêm địa chỉ mới");
            }
        } catch (Exception e) {
            logger.error("Error saving address: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi lưu địa chỉ");
        }

        return "redirect:/profile";
    }

    @PostMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(userDetails.getUsername());
            Address address = addressService.getAddressById(id);

            // Check if address exists and belongs to the user
            if (address != null && address.getUser().getId().equals(user.getId())) {
                addressService.deleteAddress(id);
                redirectAttributes.addFlashAttribute("success", "Đã xóa địa chỉ");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa địa chỉ này");
            }
        } catch (Exception e) {
            logger.error("Error deleting address: ", e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa địa chỉ");
        }

        return "redirect:/profile";
    }

    // Fixed: Removed duplicate "/profile" in the mapping path
    @GetMapping("/address/{id}")
    @ResponseBody
    public ResponseEntity<Address> getUserAddress(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Address address = addressService.getAddressById(id);
            if (address == null) {
                return ResponseEntity.notFound().build();
            }

            if (!address.getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(address);
        } catch (Exception e) {
            logger.error("Error fetching address: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}