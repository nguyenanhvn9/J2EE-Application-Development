package com.hutech.cos141_demo.service;

import org.springframework.stereotype.Service;
import com.hutech.cos141_demo.model.TodoItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private List<TodoItem> items = new ArrayList<>();
    private Long nextId = 1L;

    // Lấy tất cả items với filter
    public List<TodoItem> getAllItems(String filter) {
        if (filter == null || filter.equals("all")) {
            return items;
        } else if (filter.equals("active")) {
            return items.stream()
                    .filter(item -> !item.isCompleted())
                    .collect(Collectors.toList());
        } else if (filter.equals("completed")) {
            return items.stream()
                    .filter(TodoItem::isCompleted)
                    .collect(Collectors.toList());
        }
        return items;
    }

    // Lấy tất cả items
    public List<TodoItem> getAllItems() {
        return getAllItems("all");
    }

    // Đếm số công việc chưa hoàn thành
    public int countActiveItems() {
        return (int) items.stream()
                .filter(item -> !item.isCompleted())
                .count();
    }

    // Xóa tất cả công việc đã hoàn thành
    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

    // Thêm item mới
    public TodoItem addItem(TodoItem item) {
        item.setId(nextId++);
        items.add(item);
        return item;
    }

    // Chuyển đổi trạng thái hoàn thành
    public boolean toggleCompleted(Long id) {
        Optional<TodoItem> item = items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
        
        if (item.isPresent()) {
            item.get().setCompleted(!item.get().isCompleted());
            return true;
        }
        return false;
    }

    // Xóa item
    public boolean deleteItem(Long id) {
        return items.removeIf(item -> item.getId().equals(id));
    }
}
