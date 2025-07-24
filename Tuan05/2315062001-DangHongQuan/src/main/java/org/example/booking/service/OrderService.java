package org.example.booking.service;

import org.example.booking.Response.OrderRepository;
import org.example.booking.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    public Order createOrder(User user, String customerName, String shippingAddress,
                             String customerPhone, BigDecimal shippingFee) {

        // Validate cart
        shoppingCartService.validateCartForCheckout(user);

        ShoppingCart cart = shoppingCartService.getCartByUser(user);
        if (cart == null || cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Calculate total amount
        BigDecimal subtotal = cart.getTotalAmount();
        BigDecimal totalAmount = subtotal.add(shippingFee != null ? shippingFee : BigDecimal.ZERO);

        // Create order
        Order order = new Order(user, totalAmount, customerName, shippingAddress, customerPhone);
        order = orderRepository.save(order);

        // Create order items and reduce stock
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            // Create order item
            OrderItem orderItem = new OrderItem(order, product, cartItem.getQuantity(), product.getPrice());
            order.addOrderItem(orderItem);

            // Reduce stock
            productService.reduceStock(product.getId(), cartItem.getQuantity());
        }

        // Save order with items
        order = orderRepository.save(order);

        // Clear cart
        shoppingCartService.clearCart(user);

        return order;
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Page<Order> getOrdersByUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByUserOrderByOrderDateDesc(user, pageable);
    }

    // Admin methods
    public Page<Order> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getOrdersByStatus(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByStatusOrderByOrderDateDesc(status, pageable);
    }

    public Page<Order> searchOrders(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByCustomerNameContainingIgnoreCaseOrCustomerPhoneContainingOrderByOrderDateDesc(
                searchTerm, searchTerm, pageable);
    }

    public Page<Order> searchOrdersByStatus(String status, String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByStatusAndSearch(status, searchTerm, pageable);
    }

    public void updateOrderStatus(Long orderId, String newStatus, String updatedBy) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String oldStatus = order.getStatus();
        order.updateStatus(newStatus, updatedBy);

        // If order is cancelled, restore stock
        if ("CANCELLED".equals(newStatus)) {
            restoreStockFromOrder(order);
        }

        orderRepository.save(order);
    }

    private void restoreStockFromOrder(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            productService.increaseStock(item.getProduct().getId(), item.getQuantity());
        }
    }

    public List<Order> getRecentOrders() {
        return orderRepository.findTop10ByOrderByOrderDateDesc();
    }

    // Statistics methods
    public long getOrderCountByStatus(String status) {
        return orderRepository.countByStatus(status);
    }

    public BigDecimal getTotalRevenue() {
        BigDecimal revenue = orderRepository.getTotalRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public BigDecimal getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal revenue = orderRepository.getRevenueByDateRange(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetweenOrderByOrderDateDesc(startDate, endDate);
    }
}