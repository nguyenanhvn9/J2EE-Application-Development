// com.example.demo.propertyeditor/CategoryPropertyEditor.java (tạo package mới)
package com.example.demo.propertyeditor;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository; // Cần CategoryRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; // Quan trọng để Spring quản lý bean này
import java.beans.PropertyEditorSupport;
import java.util.Optional;

// Đặt @Component để Spring có thể inject nó vào Controller
// Hoặc bạn có thể tạo instance trực tiếp trong @InitBinder
@Component
public class CategoryPropertyEditor extends PropertyEditorSupport {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryPropertyEditor(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(null); // Đặt giá trị là null nếu không có ID
            return;
        }
        try {
            Long id = Long.parseLong(text);
            Optional<Category> category = categoryRepository.findById(id);
            // Nếu tìm thấy, đặt Category object. Nếu không, đặt null để @NotNull xử lý
            setValue(category.orElse(null));
        } catch (NumberFormatException e) {
            // Xử lý trường hợp chuỗi không phải số
            setValue(null);
            System.err.println("Invalid category ID format: " + text);
        }
    }

    // Tùy chọn: Để hiển thị giá trị hiện tại (ví dụ, trong trường hợp lỗi form),
    // bạn có thể override getAsText()
    // @Override
    // public String getAsText() {
    //     Category category = (Category) getValue();
    //     return (category != null ? category.getId().toString() : "");
    // }
}