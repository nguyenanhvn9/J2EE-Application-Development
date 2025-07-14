package com.example.todo_app.service;

import com.example.todo_app.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> todoItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<TodoItem> getAllItems() {
        return todoItems;
    }
    
    public List<TodoItem> getFilteredItems(String filter) {
        if (filter == null) {
            return todoItems;
        }
        
        switch (filter) {
            case "active":
                return todoItems.stream()
                        .filter(item -> !item.isCompleted())
                        .collect(Collectors.toList());
            case "completed":
                return todoItems.stream()
                        .filter(TodoItem::isCompleted)
                        .collect(Collectors.toList());
            default:
                return todoItems;
        }
    }

    public TodoItem addItem(String title) {
        TodoItem newItem = new TodoItem(counter.incrementAndGet(), title);
        todoItems.add(newItem);
        return newItem;
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
    
    public void clearCompleted() {
        todoItems.removeIf(TodoItem::isCompleted);
    }
    
    public int countActiveItems() {
        return (int) todoItems.stream()
                .filter(item -> !item.isCompleted())
                .count();
    }
}