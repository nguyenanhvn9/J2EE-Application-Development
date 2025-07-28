// src/main/java/com/example/demo/repository/ReviewRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
    Optional<Review> findByUserAndProduct(User user, Product product);
    List<Review> findByProductOrderByCreatedAtDesc(Product product); // Lấy đánh giá của sản phẩm sắp xếp theo mới nhất
}