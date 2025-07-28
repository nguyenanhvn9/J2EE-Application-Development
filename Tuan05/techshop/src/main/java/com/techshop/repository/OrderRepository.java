package com.techshop.repository;

import com.techshop.model.Order;
import com.techshop.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerNameContainingOrPhoneContaining(String name, String phone);

    List<Order> findByPhone(String phone);

    List<Order> findByUserId(Long userId);
}