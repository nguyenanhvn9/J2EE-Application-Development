package org.example.booking.Response;

import org.example.booking.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find active categories
    List<Category> findByIsActiveTrueOrderByName();

    // Find categories with products count
    @Query("SELECT c FROM Category c WHERE c.isActive = true AND SIZE(c.products) > 0 ORDER BY c.name")
    List<Category> findActiveCategoriesWithProducts();

    // Check if category name exists
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    boolean existsByNameIgnoreCase(String name);

    // Find by name
    Category findByNameIgnoreCase(String name);
}