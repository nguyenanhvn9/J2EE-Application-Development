package org.example.booking.service;
import org.example.booking.Response.CategoryRepository;
import org.example.booking.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllActiveCategories() {
        return categoryRepository.findByIsActiveTrueOrderByName();
    }

    public List<Category> getActiveCategoriesWithProducts() {
        return categoryRepository.findActiveCategoriesWithProducts();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean isCategoryNameExists(String name, Long excludeId) {
        if (excludeId != null) {
            return categoryRepository.existsByNameIgnoreCaseAndIdNot(name, excludeId);
        }
        return categoryRepository.existsByNameIgnoreCase(name);
    }
}