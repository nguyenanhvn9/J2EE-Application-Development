package org.example.booking.Response;

import org.example.booking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
        
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}

