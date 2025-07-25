package com.techshop.service;

import com.techshop.model.Order;
import com.techshop.model.OrderStatus;
import com.techshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.techshop.model.OrderItem;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> searchByCustomer(String keyword) {
        return orderRepository.findByCustomerNameContainingOrPhoneContaining(keyword, keyword);
    }

    public List<Order> findByPhone(String phone) {
        return orderRepository.findByPhone(phone);
    }

    public boolean hasUserPurchasedProduct(Long userId, Long productId) {
        // Giả sử Order có trường userId và OrderItem có product
        List<Order> orders = orderRepository.findByUserId(userId);
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Map<String, Object>> getRevenueLast7Days() {
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            double revenue = orderRepository.findAll().stream()
                    .filter(o -> o.getCreatedAt().toLocalDate().equals(day))
                    .mapToDouble(o -> o.getTotalAmount().doubleValue())
                    .sum();
            Map<String, Object> map = new HashMap<>();
            map.put("date", day.toString());
            map.put("revenue", revenue);
            result.add(map);
        }
        return result;
    }

    public int countOrdersLast24h() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        return (int) orderRepository.findAll().stream()
                .filter(o -> o.getCreatedAt().isAfter(since))
                .count();
    }
}