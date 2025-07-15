package com.example.todoapp.service;

import com.example.todoapp.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<TodoItem> getAll() {
        return items;
    }

    public void add(String title) {
        items.add(new TodoItem(counter.getAndIncrement(), title, false));
    }

    public void toggle(Long id) {
        items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setCompleted(!item.isCompleted()));
    }

    public void delete(Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

    public List<TodoItem> filter(String filter) {
        if (filter == null) return items;

        return switch (filter) {
            case "active" -> items.stream().filter(i -> !i.isCompleted()).toList();
            case "completed" -> items.stream().filter(TodoItem::isCompleted).toList();
            default -> items;
        };
    }

    public long countActive() {
        return items.stream().filter(i -> !i.isCompleted()).count();
    }
}
