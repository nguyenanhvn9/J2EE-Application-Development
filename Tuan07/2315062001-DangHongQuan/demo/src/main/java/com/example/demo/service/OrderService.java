// Trong com.example.demo.service.OrderService.java

package com.example.demo.service;

import com.example.demo.entity.*; // Contains Order, CartItem, Product, User, OrderItem, Store
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set; // Import Set
import java.util.HashSet; // Import HashSet
import java.util.stream.Collectors;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private VoucherService voucherService;

    @Transactional
    public Order createOrder(User user,
                             String customerName,
                             String shippingAddress,
                             String customerPhone,
                             String shippingMethodType,
                             Order.DeliveryMethod deliveryMethod,
                             Long pickupStoreId,
                             List<Long> selectedCartItemIds,
                             String voucherCode,
                             BigDecimal shippingFeeFromClient,
                             BigDecimal discountAmountFromClient
    ) {
        List<CartItem> selectedCartItems = cartService.getSelectedCartItemsForCheckout(user, selectedCartItemIds);

        if (selectedCartItems.isEmpty()) {
            throw new RuntimeException("Không có sản phẩm nào được chọn để thanh toán.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        // ĐẢM BẢO LUÔN SET customerName VÀ customerPhone Ở ĐÂY
        order.setCustomerName(customerName);
        order.setCustomerPhone(customerPhone);

        order.setDeliveryMethod(deliveryMethod);
        if (deliveryMethod == Order.DeliveryMethod.DELIVERY) {
            order.setShippingAddress(shippingAddress); // Chỉ địa chỉ giao hàng là có điều kiện
            order.setShippingMethod(Order.ShippingMethod.valueOf(shippingMethodType));
            order.setShippingFee(shippingFeeFromClient);
        } else { // PICKUP
            if (pickupStoreId != null) {
                Store pickupStore = storeRepository.findById(pickupStoreId)
                        .orElseThrow(() -> new RuntimeException("Cửa hàng nhận hàng không tồn tại."));
                order.setPickupStore(pickupStore);
            } else {
                throw new RuntimeException("Vui lòng chọn cửa hàng để nhận hàng.");
            }
            order.setShippingFee(BigDecimal.ZERO);
            order.setShippingMethod(null); // Không có phương thức vận chuyển cho pickup
            order.setShippingAddress(null); // Không có địa chỉ giao hàng cho pickup
        }

        BigDecimal initialProductsTotal = selectedCartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal actualDiscountAmount = BigDecimal.ZERO;
        if (voucherCode != null && !voucherCode.trim().isEmpty()) {
            actualDiscountAmount = voucherService.validateAndCalculateDiscount(voucherCode, initialProductsTotal.add(order.getShippingFee()));
            order.setVoucherCode(voucherCode);
            order.setDiscountAmount(actualDiscountAmount);
        } else {
            order.setVoucherCode(null);
            order.setDiscountAmount(BigDecimal.ZERO);
        }

        BigDecimal finalTotalAmount = initialProductsTotal
                .add(order.getShippingFee())
                .subtract(actualDiscountAmount);
        order.setTotalAmount(finalTotalAmount);

        Set<OrderItem> orderItems = selectedCartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPricePerUnit(cartItem.getPrice());
                    return orderItem;
                }).collect(Collectors.toCollection(HashSet::new));
        order.setOrderItems(orderItems);

        for (CartItem cartItem : selectedCartItems) {
            Product product = cartItem.getProduct();
            int newStock = product.getStockQuantity() - cartItem.getQuantity();
            if (newStock < 0) {
                throw new RuntimeException("Sản phẩm '" + product.getName() + "' không đủ số lượng trong kho.");
            }
            product.setStockQuantity(newStock);
            productService.saveProduct(product);
        }

        Order savedOrder = orderRepository.save(order);

        selectedCartItems.forEach(cartItem -> cartService.removeCartItem(cartItem.getId()));

        if (order.getVoucherCode() != null && actualDiscountAmount.compareTo(BigDecimal.ZERO) > 0) {
            voucherService.incrementVoucherUsage(order.getVoucherCode());
        }

        return savedOrder;
    }

    // --- KEEP THIS ONE (from CustomerController usage) ---
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(User user) {
        if (user == null) {
            return List.of(); // Return empty list for null user
        }
        return orderRepository.findByUserOrderByOrderDateDesc(user); // Assuming this method exists in OrderRepository
    }

    // --- KEEP THIS ONE (from CustomerController usage) ---
    @Transactional(readOnly = true)
    public Order getOrderDetailsForUser(Long orderId, User user) {
        if (orderId == null || user == null) {
            return null;
        }
        return orderRepository.findById(orderId)
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .map(order -> {
                    order.getOrderItems().size(); // Force fetching order items if LAZY
                    return order;
                })
                .orElse(null);
    }

    // This method is fine, signature is different from getOrdersByUser(User)
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return orderRepository.findByUserOrderByOrderDateDesc(user); // Assuming this method exists in OrderRepository
    }

    // Cập nhật phương thức calculateTotal (Đây là một phương thức riêng tư, không phải lỗi duplicate)
    private BigDecimal calculateTotal(List<CartItem> items, String shippingMethod, Order.DeliveryMethod deliveryMethod) {
        BigDecimal total = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (deliveryMethod == Order.DeliveryMethod.DELIVERY) {
            if ("STANDARD".equalsIgnoreCase(shippingMethod)) {
                total = total.add(new BigDecimal("30000.00"));
            } else if ("EXPRESS".equalsIgnoreCase(shippingMethod)) {
                total = total.add(new BigDecimal("50000.00"));
            }
        }
        return total;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.getOrderItems().size();
                    return order;
                })
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Order> searchOrders(String keyword) {
        return orderRepository.findByCustomerNameContainingIgnoreCaseOrCustomerPhoneContainingIgnoreCase(keyword, keyword);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus, String updatedBy) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại với ID: " + orderId));

        if (!isValidStatusTransition(order.getStatus(), newStatus)) {
            throw new RuntimeException("Chuyển đổi trạng thái từ '" + order.getStatus() + "' sang '" + newStatus + "' không hợp lệ.");
        }

        order.setStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(updatedBy);
        orderRepository.save(order);
    }

    private boolean isValidStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.PENDING) {
            return newStatus == OrderStatus.PROCESSING || newStatus == OrderStatus.CANCELLED;
        } else if (currentStatus == OrderStatus.PROCESSING) {
            return newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELLED;
        } else if (currentStatus == OrderStatus.SHIPPED) {
            return newStatus == OrderStatus.DELIVERED;
        } else if (currentStatus == OrderStatus.DELIVERED || currentStatus == OrderStatus.CANCELLED) {
            return false;
        }
        return false;
    }

    // --- NEW: Phương thức để hủy đơn hàng ---
    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findByIdAndUser(orderId, user)
                .orElseThrow(() -> new IllegalArgumentException("Đơn hàng không tồn tại hoặc bạn không có quyền truy cập."));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Chỉ có thể hủy đơn hàng đang ở trạng thái Chờ xử lý.");
        }

        // Cập nhật trạng thái đơn hàng thành CANCELLED
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(user.getUsername()); // Hoặc admin nếu admin hủy

        // Hoàn trả số lượng sản phẩm vào kho
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productService.saveProduct(product);
        }

        // Hoàn trả lượt sử dụng voucher nếu có
        if (order.getVoucherCode() != null && !order.getVoucherCode().isEmpty()) {
            try {
                voucherService.returnVoucherUsage(order.getVoucherCode());
            } catch (Exception e) {
                // Log lỗi nhưng không chặn việc hủy đơn hàng
                System.err.println("Lỗi khi hoàn trả lượt sử dụng voucher " + order.getVoucherCode() + ": " + e.getMessage());
            }
        }

        orderRepository.save(order);
    }
}