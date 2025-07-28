// com.example.demo.converter/CategoryByIdConverter.java
package com.example.demo.converter;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryByIdConverter implements Converter<String, Category> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryByIdConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category convert(String idString) {
        if (idString == null || idString.trim().isEmpty()) {
            return null;
        }
        try {
            Long id = Long.parseLong(idString);
            Optional<Category> category = categoryRepository.findById(id);
            return category.orElse(null); // Trả về null nếu không tìm thấy, để @NotNull bắt lỗi
        } catch (NumberFormatException e) {
            // Xử lý trường hợp chuỗi không phải là số hợp lệ
            return null;
        }
    }
}