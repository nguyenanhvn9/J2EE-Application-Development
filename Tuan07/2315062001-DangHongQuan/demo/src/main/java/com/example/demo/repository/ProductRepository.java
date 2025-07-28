package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop6ByIsActiveTrueOrderByCreatedAtDesc();
    @Query("SELECT p FROM Product p JOIN p.orderItems oi GROUP BY p ORDER BY SUM(oi.quantity) DESC")
    List<Product> findTop6ByOrderItemsQuantity();

    List<Product> findByCategoryId(Long categoryId);
    // Tìm kiếm theo tên sản phẩm (gần đúng) và có phân trang
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Lọc theo categoryId và có phân trang
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // Tìm kiếm theo tên VÀ lọc theo categoryId, có phân trang
    // Sử dụng @Query vì có thể cần kết hợp cả 2 điều kiện
    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> findByKeywordAndCategoryId(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);
}