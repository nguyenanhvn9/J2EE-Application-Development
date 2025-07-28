package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCustomerNameContainingIgnoreCaseOrCustomerPhoneContainingIgnoreCase(String name, String phone);
    List<Order> findByUserOrderByOrderDateDesc(User user);
    // Thêm phương thức này vào OrderRepository
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    // THÊM PHƯƠNG THỨC NÀY
    Optional<Order> findByIdAndUser(Long id, User user);
}