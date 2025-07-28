package com.techshop.service;

import com.techshop.model.Category;
import com.techshop.repository.CategoryRepository;
import com.techshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        // Kiểm tra danh mục có sản phẩm liên kết không
        if (!productRepository.findByCategory_Id(id).isEmpty()) {
            throw new RuntimeException("Không thể xóa danh mục vì đã có sản phẩm liên quan!");
        }
        categoryRepository.deleteById(id);
    }
}