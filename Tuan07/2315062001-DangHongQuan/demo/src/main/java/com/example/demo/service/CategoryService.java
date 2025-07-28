package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Import Optional

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the database.
     * @return A list of all Category entities.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves a single category by its ID.
     * @param id The ID of the category to retrieve.
     * @return The Category entity if found, otherwise null.
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a list of featured categories.
     * This method assumes a custom query or sorting in CategoryRepository to define "featured".
     * If no specific "featured" logic is needed, it might just return a subset or all categories.
     * @return A list of featured Category entities.
     */
    public List<Category> getFeaturedCategories() {
        // This line assumes you have a method in CategoryRepository like:
        // List<Category> findTop6ByOrderByNameAsc();
        // If not, you'll need to define it or adjust this logic.
        // For example, if you just want top 6 by ID:
        // return categoryRepository.findAll(PageRequest.of(0, 6, Sort.by("id").descending())).getContent();

        // Assuming findTop6ByOrderByNameAsc() exists and works as intended for featured categories
        return categoryRepository.findTop6ByOrderByNameAsc();
    }

    /**
     * Saves a Category entity to the database.
     * This method can be used for both creating new categories and updating existing ones.
     * @param category The Category entity to save.
     * @return The saved Category entity.
     */
    public Category saveCategory(Category category) {
        // You might add validation or business logic here before saving
        return categoryRepository.save(category);
    }

    /**
     * Deletes a category by its ID.
     * @param id The ID of the category to delete.
     * @throws RuntimeException if the category is not found.
     */
    public void deleteCategory(Long id) {
        // It's good practice to check if the category exists before attempting to delete.
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}