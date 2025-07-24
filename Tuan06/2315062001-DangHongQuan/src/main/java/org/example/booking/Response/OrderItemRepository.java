package org.example.booking.Response;


import org.example.booking.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    boolean existsByOrderUserIdAndProductId(Long userId, Long productId);

}

