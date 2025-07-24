package org.example.booking.Response;


import org.example.booking.model.Category;
import org.example.booking.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find active products
    Page<Product> findByIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);

    // Find products by category
    Page<Product> findByCategoryAndIsActiveTrueOrderByCreatedAtDesc(Category category, Pageable pageable);

    // Search products by name
    Page<Product> findByNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedAtDesc(String name, Pageable pageable);

    // Find products by category and search term
    Page<Product> findByCategoryAndNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedAtDesc(
            Category category, String name, Pageable pageable);

    // Find latest products
    List<Product> findTop8ByIsActiveTrueOrderByCreatedAtDesc();

    // Find best selling products (most ordered)
    @Query("SELECT p FROM Product p JOIN p.orderItems oi " +
            "WHERE p.isActive = true " +
            "GROUP BY p.id " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Product> findBestSellingProducts(Pageable pageable);

    // Find products with low stock
    List<Product> findByStockQuantityLessThanAndIsActiveTrue(Integer threshold);

    // Search for admin - includes inactive products
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find by category for admin
    Page<Product> findByCategory(Category category, Pageable pageable);

    // Count products by category
    long countByCategoryAndIsActiveTrue(Category category);

    // Check if product name exists (for validation)
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    boolean existsByNameIgnoreCase(String name);
}