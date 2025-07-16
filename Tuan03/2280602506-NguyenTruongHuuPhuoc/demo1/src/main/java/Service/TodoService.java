package com.example.demo.service;

import com.example.demo.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<TodoItem> getAllItems() {
        return items;
    }

    public void addItem(TodoItem item) {
        item.setId(counter.incrementAndGet());
        items.add(item);
    }

    public void toggleCompleted(Long id) {
        items.stream().filter(i -> i.getId().equals(id)).findFirst()
                .ifPresent(i -> i.setCompleted(!i.isCompleted()));
    }

    public void deleteItem(Long id) {
        items.removeIf(i -> i.getId().equals(id));
    }
    // Trả về số lượng công việc chưa hoàn thành
    public long countActiveItems() {
        return items.stream().filter(item -> !item.isCompleted()).count();
    }

    // Trả về danh sách theo bộ lọc
    public List<TodoItem> getFilteredItems(String filter) {
        return switch (filter) {
            case "active" -> items.stream().filter(i -> !i.isCompleted()).toList();
            case "completed" -> items.stream().filter(TodoItem::isCompleted).toList();
            default -> items;
        };
    }

    // Xoá tất cả công việc đã hoàn thành
    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

}
