package org.example.booking.Response;

import org.example.booking.model.Order;
import org.example.booking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by user
    Page<Order> findByUserOrderByOrderDateDesc(User user, Pageable pageable);

    // Find orders by status
    Page<Order> findByStatusOrderByOrderDateDesc(String status, Pageable pageable);

    // Search orders by customer name or phone
    Page<Order> findByCustomerNameContainingIgnoreCaseOrCustomerPhoneContainingOrderByOrderDateDesc(
            String customerName, String customerPhone, Pageable pageable);

    // Find orders by status and search
    @Query("SELECT o FROM Order o WHERE o.status = :status AND " +
            "(LOWER(o.customerName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "o.customerPhone LIKE CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY o.orderDate DESC")
    Page<Order> findByStatusAndSearch(@Param("status") String status,
                                      @Param("searchTerm") String searchTerm,
                                      Pageable pageable);

    // Find recent orders
    List<Order> findTop10ByOrderByOrderDateDesc();

    // Find orders by date range
    List<Order> findByOrderDateBetweenOrderByOrderDateDesc(LocalDateTime startDate, LocalDateTime endDate);

    // Count orders by status
    long countByStatus(String status);

    // Get total revenue
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'DELIVERED'")
    java.math.BigDecimal getTotalRevenue();

    // Get revenue by date range
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'DELIVERED' AND o.orderDate BETWEEN :startDate AND :endDate")
    java.math.BigDecimal getRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    // Find delivered orders of a specific user
    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.status = 'DELIVERED' ORDER BY o.orderDate DESC")
    List<Order> findDeliveredOrdersByUser(@Param("user") User user);
    List<Order> findByUserIdAndStatus(Long userId, String status);


    @Query("SELECT COUNT(o) > 0 FROM Order o " +
            "JOIN o.items i " +
            "WHERE o.user.id = :userId AND i.product.id = :productId " +
            "AND o.status IN ('DELIVERED', 'COMPLETED')")
    boolean hasUserPurchasedProduct(@Param("userId") Long userId,
                                    @Param("productId") Long productId);
}