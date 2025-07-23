package com.techshop.controller;

import com.techshop.model.CartItem;
import com.techshop.model.Product;
import com.techshop.model.Order;
import com.techshop.model.OrderItem;
import com.techshop.model.OrderStatus;
import com.techshop.service.ProductService;
import com.techshop.service.OrderService;
import com.techshop.util.ControllerUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.techshop.repository.UserRepository;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product == null)
            return "redirect:/";
        model.addAttribute("product", product);
        return "product_detail";
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCartFromSession(HttpSession session) {
        Object cartObj = session.getAttribute("cart");
        if (cartObj instanceof List<?>) {
            try {
                return (List<CartItem>) cartObj;
            } catch (ClassCastException e) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getOrderConfirmInfo(HttpSession session) {
        Object infoObj = session.getAttribute("orderConfirmInfo");
        if (infoObj instanceof Map<?, ?>) {
            try {
                return (Map<String, Object>) infoObj;
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Product product = productService.getProduct(productId);
        if (product == null)
            return "redirect:/";
        List<CartItem> cart = getCartFromSession(session);
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new CartItem(product.getId(), product.getName(), product.getImageUrl(), product.getPrice(),
                    quantity));
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        ControllerUtils.addUsername(model);
        List<CartItem> cart = getCartFromSession(session);
        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session) {
        List<CartItem> cart = getCartFromSession(session);
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkoutForm(HttpSession session, Model model) {
        ControllerUtils.addUsername(model);
        List<CartItem> cart = getCartFromSession(session);
        if (cart.isEmpty())
            return "redirect:/cart";
        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String customerName,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String shippingMethod,
            HttpSession session, Model model) {
        ControllerUtils.addUsername(model);
        List<CartItem> cart = getCartFromSession(session);
        if (cart.isEmpty())
            return "redirect:/cart";
        BigDecimal shippingFee = "Hỏa tốc".equals(shippingMethod) ? new BigDecimal("50000") : new BigDecimal("30000");
        BigDecimal total = cart.stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("totalWithFee", total.add(shippingFee));
        model.addAttribute("customerName", customerName);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        model.addAttribute("address", address);
        model.addAttribute("shippingMethod", shippingMethod);
        // Lưu thông tin khách hàng tạm vào session để dùng cho bước xác nhận
        session.setAttribute("orderConfirmInfo", Map.of(
                "customerName", customerName,
                "phone", phone,
                "email", email,
                "address", address,
                "shippingMethod", shippingMethod,
                "shippingFee", shippingFee,
                "total", total,
                "totalWithFee", total.add(shippingFee)));
        return "order_confirm";
    }

    @PostMapping("/checkout/confirm")
    public String confirmOrder(HttpSession session, Model model) {
        ControllerUtils.addUsername(model);
        logger.info("Bắt đầu xác nhận đặt hàng");
        List<CartItem> cart = getCartFromSession(session);
        if (cart == null || cart.isEmpty()) {
            logger.warn("Cart rỗng khi xác nhận đặt hàng");
            model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
            model.addAttribute("cart", cart);
            model.addAttribute("total", BigDecimal.ZERO);
            model.addAttribute("shippingFee", BigDecimal.ZERO);
            model.addAttribute("totalWithFee", BigDecimal.ZERO);
            return "order_confirm";
        }
        Map<String, Object> info = getOrderConfirmInfo(session);
        if (info == null) {
            logger.warn("orderConfirmInfo null trong session");
            model.addAttribute("error", "Thông tin xác nhận đơn hàng không hợp lệ. Vui lòng thử lại!");
            model.addAttribute("cart", cart);
            model.addAttribute("total", BigDecimal.ZERO);
            model.addAttribute("shippingFee", BigDecimal.ZERO);
            model.addAttribute("totalWithFee", BigDecimal.ZERO);
            return "order_confirm";
        }
        try {
            String customerName = (String) info.get("customerName");
            String phone = (String) info.get("phone");
            String email = (String) info.get("email");
            String address = (String) info.get("address");
            String shippingMethod = (String) info.get("shippingMethod");
            Object shippingFeeObj = info.get("shippingFee");
            BigDecimal shippingFee;
            if (shippingFeeObj instanceof BigDecimal) {
                shippingFee = (BigDecimal) shippingFeeObj;
            } else if (shippingFeeObj instanceof Number) {
                shippingFee = BigDecimal.valueOf(((Number) shippingFeeObj).doubleValue());
            } else {
                shippingFee = BigDecimal.ZERO;
            }
            Object totalObj = info.get("total");
            BigDecimal total;
            if (totalObj instanceof BigDecimal) {
                total = (BigDecimal) totalObj;
            } else if (totalObj instanceof Number) {
                total = BigDecimal.valueOf(((Number) totalObj).doubleValue());
            } else {
                total = BigDecimal.ZERO;
            }
            Order order = new Order();
            order.setCustomerName(customerName);
            order.setPhone(phone);
            order.setEmail(email);
            order.setAddress(address);
            order.setShippingMethod(shippingMethod);
            order.setShippingFee(shippingFee);
            order.setTotalAmount(total.add(shippingFee));
            order.setStatus(OrderStatus.PENDING);
            // Set userId nếu đã đăng nhập
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String username = auth.getName();
                if (userRepository != null && username != null) {
                    com.techshop.model.User user = userRepository.findByUsername(username);
                    if (user != null) {
                        order.setUserId(user.getId());
                    }
                }
            }
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem item : cart) {
                Product product = productService.getProduct(item.getProductId());
                if (product == null) {
                    logger.error("Không tìm thấy sản phẩm với id: {}", item.getProductId());
                    model.addAttribute("error", "Sản phẩm trong giỏ hàng không tồn tại!");
                    model.addAttribute("cart", cart);
                    model.addAttribute("total", total);
                    model.addAttribute("shippingFee", shippingFee);
                    model.addAttribute("totalWithFee", total.add(shippingFee));
                    model.addAttribute("customerName", customerName);
                    model.addAttribute("phone", phone);
                    model.addAttribute("email", email);
                    model.addAttribute("address", address);
                    model.addAttribute("shippingMethod", shippingMethod);
                    return "order_confirm";
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());
                orderItem.setOrder(order);
                orderItems.add(orderItem);
                // Trừ kho
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                productService.saveProduct(product);
            }
            order.setItems(orderItems);
            orderService.saveOrder(order);
            // Chỉ xóa session sau khi lưu thành công
            session.removeAttribute("cart");
            session.removeAttribute("orderConfirmInfo");
            model.addAttribute("order", order);
            session.setAttribute("customerPhone", phone); // Lưu số điện thoại vào session
            logger.info("Đặt hàng thành công cho khách: {}", customerName);
            return "order_success";
        } catch (Exception e) {
            logger.error("Lỗi khi xác nhận đặt hàng", e);
            // Truyền lại đầy đủ thông tin để user không phải nhập lại
            model.addAttribute("error", "Có lỗi xảy ra khi xác nhận đặt hàng. Vui lòng thử lại!");
            model.addAttribute("cart", cart);
            model.addAttribute("total", BigDecimal.ZERO);
            model.addAttribute("shippingFee", BigDecimal.ZERO);
            model.addAttribute("totalWithFee", BigDecimal.ZERO);
            if (info != null) {
                model.addAttribute("customerName", info.get("customerName"));
                model.addAttribute("phone", info.get("phone"));
                model.addAttribute("email", info.get("email"));
                model.addAttribute("address", info.get("address"));
                model.addAttribute("shippingMethod", info.get("shippingMethod"));
            }
            return "order_confirm";
        }
    }
}