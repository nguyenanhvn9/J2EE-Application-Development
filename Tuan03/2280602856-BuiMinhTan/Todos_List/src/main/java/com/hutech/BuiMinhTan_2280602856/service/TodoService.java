package com.hutech.BuiMinhTan_2280602856.service;

import com.hutech.BuiMinhTan_2280602856.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> todoItems = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TodoService() {
        addItem("Học Spring Boot");
        addItem("Làm bài tập Thymeleaf");
        addItem("Đọc sách lập trình");
    }

    public List<TodoItem> getAllItems() {
        return new ArrayList<>(todoItems);
    }

    public List<TodoItem> getFilteredItems(String filter) {
        if (filter == null || filter.equals("all")) {
            return getAllItems();
        } else if (filter.equals("active")) {
            return todoItems.stream()
                    .filter(item -> !item.isCompleted())
                    .collect(Collectors.toList());
        } else if (filter.equals("completed")) {
            return todoItems.stream()
                    .filter(TodoItem::isCompleted)
                    .collect(Collectors.toList());
        }
        return getAllItems();
    }

    public void addItem(String title) {
        if (title != null && !title.trim().isEmpty()) {
            TodoItem newItem = new TodoItem(idGenerator.getAndIncrement(), title.trim());
            todoItems.add(newItem);
        }
    }

    public void toggleCompleted(Long id) {
        todoItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setCompleted(!item.isCompleted()));
    }

    public void deleteItem(Long id) {
        todoItems.removeIf(item -> item.getId().equals(id));
    }

    public long getActiveItemsCount() {
        return todoItems.stream()
                .filter(item -> !item.isCompleted())
                .count();
    }

    public void clearCompleted() {
        todoItems.removeIf(TodoItem::isCompleted);
    }
}
