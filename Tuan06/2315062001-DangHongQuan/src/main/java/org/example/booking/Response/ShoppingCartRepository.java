package org.example.booking.Response;


import org.example.booking.model.ShoppingCart;
import org.example.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    // Find cart by user
    Optional<ShoppingCart> findByUser(User user);

    // Find cart by user id
    Optional<ShoppingCart> findByUserId(Long userId);

    // Delete cart by user
    void deleteByUser(User user);
}