package com.example.demo.service;

import java.util.*;

import com.example.demo.model.TodoItem;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private Long currentId = 1L;

    public List<TodoItem> getAllItems() {
        return items;
    }

    public void addItem(String title) {
        TodoItem item = new TodoItem();
        item.setId(currentId++);
        item.setTitle(title);
        item.setCompleted(false);
        items.add(item);
    }

    public void toggleCompleted(Long id) {
        items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setCompleted(!item.isCompleted()));
    }

    public void deleteItem(Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public int countUncompleted() {
        return (int) items.stream().filter(item -> !item.isCompleted()).count();
    }

    public List<TodoItem> getFilteredItems(String filter) {
        // ✅ Fix lỗi NullPointerException khi filter == null
        if (filter == null) {
            return getAllItems();
        }

        return switch (filter) {
            case "active" -> items.stream().filter(i -> !i.isCompleted()).toList();
            case "completed" -> items.stream().filter(TodoItem::isCompleted).toList();
            default -> getAllItems();
        };
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }
}
