package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Order;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.entity.Review;
import com.example.demo.service.AddressService;
import com.example.demo.service.StoreService;
import com.example.demo.entity.Address;
import com.example.demo.entity.Store;
import com.example.demo.dto.AddressDto;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections; // For Collections.emptyList()

@Controller
public class CustomerController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VoucherService voucherService; // Autowire VoucherService

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private StoreService storeService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "logoutSuccess", required = false) String logoutSuccess) {
        model.addAttribute("bestsellers", productService.getBestSellers());
        model.addAttribute("latestProducts", productService.getLatestProducts());
        model.addAttribute("categories", categoryService.getFeaturedCategories());
        if (logoutSuccess != null) {
            model.addAttribute("logoutSuccess", true);
        }
        return "home";
    }

    @GetMapping("/product/{id}")
    public String viewProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404";
        }

        List<Review> reviews = reviewService.getReviewsForProduct(id);

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", product.getAverageRating());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal()));

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.findByUsername(username);
            if (userOptional.isEmpty()) {
                return "redirect:/login?error=userNotFound";
            }
            User currentUser = userOptional.get();

            model.addAttribute("currentUser", currentUser);

            boolean hasPurchased = reviewService.hasUserPurchasedProduct(currentUser, id);
            model.addAttribute("hasPurchased", hasPurchased);

            Optional<Review> userReview = reviewService.getUserReviewForProduct(currentUser, id);
            model.addAttribute("hasReviewed", userReview.isPresent());
            if (userReview.isPresent()) {
                model.addAttribute("existingReview", userReview.get());
            } else {
                model.addAttribute("newReview", new Review());
            }
        }

        return "product-detail";
    }

    @PostMapping("/product/{productId}/submit-review")
    public String submitReview(@PathVariable Long productId,
                               @RequestParam Integer rating,
                               @RequestParam(required = false) String comment,
                               RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập để gửi đánh giá.");
            return "redirect:/login";
        }

        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin người dùng.");
            return "redirect:/product/" + productId;
        }
        User currentUser = userOptional.get();

        try {
            reviewService.submitReview(currentUser, productId, rating, comment);
            redirectAttributes.addFlashAttribute("successMessage", "Đánh giá của bạn đã được gửi thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi gửi đánh giá.");
            e.printStackTrace();
        }
        return "redirect:/product/" + productId + "#reviewsSection";
    }

    @PostMapping("/customer/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity,
                            @RequestParam(value = "buyNow", required = false) String buyNow,
                            Authentication authentication, Model model) {
        try {
            User user = (User) authentication.getPrincipal();
            Product product = productService.getProductById(productId);
            if (product == null) {
                model.addAttribute("error", "Product not found");
                model.addAttribute("product", new Product());
                return "product";
            }
            cartService.addToCart(user, product, quantity);

            if ("true".equals(buyNow)) {
                Cart cart = cartService.getCartByUser(user);
                CartItem newItem = cart.getCartItems().stream()
                        .filter(item -> item.getProduct().getId().equals(productId))
                        .findFirst()
                        .orElse(null);

                if (newItem != null) {
                    return "redirect:/checkout?selectedItems=" + newItem.getId();
                } else {
                    model.addAttribute("error", "Không thể tìm thấy sản phẩm vừa thêm vào giỏ hàng để mua ngay.");
                    model.addAttribute("product", product);
                    return "product";
                }
            }
            return "redirect:/cart?addSuccess=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            Product product = productService.getProductById(productId);
            model.addAttribute("product", product != null ? product : new Product());
            return "product";
        }
    }

    @GetMapping("/cart")
    public String cart(Model model, Authentication authentication, @RequestParam(value = "addSuccess", required = false) String addSuccess,
                       @RequestParam(value = "orderSuccess", required = false) String orderSuccess) {
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        if (addSuccess != null) {
            model.addAttribute("addSuccess", "Product added to cart successfully!");
        }
        if (orderSuccess != null) {
            model.addAttribute("orderSuccess", true);
        }
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long itemId, @RequestParam int quantity, Model model, Authentication authentication) {
        try {
            cartService.updateCartItemQuantity(itemId, quantity);
            return "redirect:/cart";
        } catch (RuntimeException e) {
            User user = (User) authentication.getPrincipal();
            Cart cart = cartService.getCartByUser(user);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cart", cart);
            return "cart";
        }
    }

    @PostMapping("/cart/remove")
    public String removeCartItem(@RequestParam Long itemId) {
        cartService.removeCartItem(itemId);
        return "redirect:/cart";
    }

    private User getCurrentAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated!");
        }
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        String username = authentication.getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found!"));
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model, Authentication authentication,
                               @RequestParam(name = "selectedItems", required = false) List<Long> selectedItemIds,
                               @RequestParam(name = "voucherCode", required = false) String voucherCode, // Thêm tham số voucherCode
                               @RequestParam(name = "voucherError", required = false) String voucherError, // Để hiển thị lỗi voucher từ POST redirect
                               @RequestParam(name = "voucherDiscount", required = false) BigDecimal voucherDiscount // Để hiển thị số tiền giảm giá đã tính
    ) {
        if (selectedItemIds == null || selectedItemIds.isEmpty()) {
            model.addAttribute("error", "Không có sản phẩm nào được chọn để thanh toán.");
            return "checkout";
        }

        User currentUser = getCurrentAuthenticatedUser(authentication);
        List<CartItem> selectedItems = cartService.getSelectedCartItemsForCheckout(currentUser, selectedItemIds);

        if (selectedItems.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy sản phẩm hợp lệ trong giỏ hàng để thanh toán.");
            return "checkout";
        }

        model.addAttribute("selectedItems", selectedItems);

        List<Address> addresses = addressService.getAddressesByUser(currentUser.getId());
        List<AddressDto> addressDtos = addresses.stream()
                .map(address -> {
                    AddressDto dto = new AddressDto();
                    dto.setId(address.getId());
                    dto.setAlias(address.getAlias());
                    dto.setRecipientName(address.getRecipientName());
                    dto.setPhoneNumber(address.getPhoneNumber());
                    dto.setStreetAddress(address.getStreetAddress());
                    dto.setWard(address.getWard());
                    dto.setDistrict(address.getDistrict());
                    dto.setCity(address.getCity());
                    dto.setDefault(address.isDefault());
                    return dto;
                }).collect(Collectors.toList());
        model.addAttribute("addresses", addressDtos);

        List<Store> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);

        // Thêm thông tin voucher vào model
        model.addAttribute("voucherCode", voucherCode);
        model.addAttribute("voucherError", voucherError);
        model.addAttribute("voucherDiscount", voucherDiscount != null ? voucherDiscount : BigDecimal.ZERO);


        // Tính toán tổng tiền sản phẩm (ban đầu) để JavaScript có thể sử dụng
        BigDecimal productsTotalPrice = selectedItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("productsTotalPrice", productsTotalPrice); // Dùng cho JavaScript

        return "checkout";
    }

    // Ví dụ trong CheckoutController.java (hoặc CustomerController)
    @PostMapping("/checkout")
    public String processCheckout(@RequestParam List<Long> selectedItemIds,
                                  @RequestParam Order.DeliveryMethod deliveryMethod,
                                  @RequestParam(required = false) Long pickupStoreId,
                                  @RequestParam(required = false) String selectedAddressId,
                                  @RequestParam(required = false) String customerName,
                                  @RequestParam(required = false) String shippingAddress,
                                  @RequestParam(required = false) String customerPhone,
                                  @RequestParam(required = false) Order.ShippingMethod shippingMethod, // Changed to enum
                                  @RequestParam(required = false) String voucherCode, // NEW: Get voucher code
                                  @RequestParam(required = false) BigDecimal shippingFee, // NEW: Get calculated shippingFee
                                  @RequestParam(required = false) BigDecimal discountAmount, // NEW: Get calculated discountAmount
                                  RedirectAttributes redirectAttributes,
                                  Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // Assuming user is in session

        try {
            // If an address from the address book was selected, use its details
            if (selectedAddressId != null && !selectedAddressId.isEmpty()) {
                Address selectedAddress = addressService.getAddressById(Long.valueOf(selectedAddressId))
                        .orElseThrow(() -> new RuntimeException("Địa chỉ không tồn tại."));
                customerName = selectedAddress.getRecipientName();
                shippingAddress = selectedAddress.getStreetAddress() + ", " + selectedAddress.getWard() + ", " + selectedAddress.getDistrict() + ", " + selectedAddress.getCity();
                customerPhone = selectedAddress.getPhoneNumber();
            }

            // Ensure shippingFee and discountAmount are not null if sent from client
            BigDecimal actualShippingFee = (shippingFee != null) ? shippingFee : BigDecimal.ZERO;
            BigDecimal actualDiscountAmount = (discountAmount != null) ? discountAmount : BigDecimal.ZERO;

            Order createdOrder = orderService.createOrder(
                    user,
                    customerName,
                    shippingAddress,
                    customerPhone,
                    (shippingMethod != null) ? shippingMethod.name() : null, // Pass enum name
                    deliveryMethod,
                    pickupStoreId,
                    selectedItemIds,
                    voucherCode,       // Pass voucher code to service
                    actualShippingFee, // Pass shipping fee from client calculation
                    actualDiscountAmount // Pass discount amount from client calculation
            );
            redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng của bạn đã được đặt thành công! Mã đơn hàng: " + createdOrder.getId());
            return "redirect:/my-orders"; // Redirect to order history
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            // Redirect back to checkout page, potentially preserving some form data if needed
            // For simplicity, just redirecting to cart here, you might want to redirect to checkout with selected items
            return "redirect:/cart?selectedItemIds=" + selectedItemIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        } catch (RuntimeException e) { // Catch other runtime exceptions like insufficient stock
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart?selectedItemIds=" + selectedItemIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        }
    }

    // --- Phương thức kiểm tra và tính toán giảm giá từ AJAX ---
    @GetMapping("/api/checkout/validate-voucher")
    @ResponseBody // Trả về JSON/data trực tiếp, không phải view
    public ResponseEntity<?> validateVoucher(@RequestParam String voucherCode,
                                             @RequestParam BigDecimal currentTotal,
                                             @RequestParam BigDecimal shippingFee) {
        try {
            // Tổng tiền để kiểm tra voucher = Tổng sản phẩm + Phí vận chuyển
            BigDecimal totalForVoucherCheck = currentTotal.add(shippingFee);
            BigDecimal discountAmount = voucherService.validateAndCalculateDiscount(voucherCode, totalForVoucherCheck);
            return ResponseEntity.ok(Collections.singletonMap("discountAmount", discountAmount));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Lỗi không xác định khi kiểm tra mã giảm giá."));
        }
    }

    @GetMapping("/my-orders")
    public String viewMyOrders(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        model.addAttribute("orders", orders);
        return "my-orders";
    }

    @GetMapping("/my-orders/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Order order = orderService.getOrderDetailsForUser(orderId, currentUser);

        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tìm thấy hoặc bạn không có quyền truy cập.");
            return "redirect:/my-orders";
        }

        model.addAttribute("order", order);
        return "order-details";
    }

    // --- NEW: Endpoint để hủy đơn hàng ---
    @PostMapping("/my-orders/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentAuthenticatedUser(authentication);

        try {
            orderService.cancelOrder(orderId, currentUser); // Gọi service để xử lý hủy đơn
            redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng #" + orderId + " đã được hủy thành công.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi hủy đơn hàng: " + e.getMessage());
        }
        return "redirect:/my-orders/" + orderId; // Quay lại trang chi tiết đơn hàng
    }

    // --- REMOVE OR COMMENT OUT THIS METHOD ---
    /*
    @PostMapping("/checkout/confirm")
    public String placeOrder(@ModelAttribute Order order,
                             @RequestParam String shippingMethod,
                             @RequestParam(value = "selectedItems", required = false) List<Long> selectedItemIds,
                             Authentication authentication,
                             Model model) {
        User user = (User) authentication.getPrincipal();
        try {
            if (selectedItemIds == null || selectedItemIds.isEmpty()) {
                model.addAttribute("error", "Vui lòng chọn ít nhất một sản phẩm để thanh toán");
                model.addAttribute("cart", cartService.getCartByUser(user));
                return "checkout";
            }

            Order createdOrder = orderService.createOrder(
                    user,
                    order.getCustomerName(),
                    order.getShippingAddress(),
                    order.getCustomerPhone(),
                    shippingMethod,
                    selectedItemIds // This is where the old signature was used
            );
            if (createdOrder == null) {
                model.addAttribute("error", "Không có sản phẩm nào được chọn hoặc không thể tạo đơn hàng");
                model.addAttribute("cart", cartService.getCartByUser(user));
                return "checkout";
            }
            return "redirect:/order/confirmation";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tạo đơn hàng: " + e.getMessage());
            model.addAttribute("cart", cartService.getCartByUser(user));
            return "checkout";
        }
    }
    */

    @GetMapping("/order/confirmation")
    public String orderConfirmation(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "order-confirmation";
    }
}