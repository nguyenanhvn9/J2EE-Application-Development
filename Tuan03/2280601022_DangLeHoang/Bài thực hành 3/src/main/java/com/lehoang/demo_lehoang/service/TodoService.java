package com.lehoang.demo_lehoang.service;

import com.lehoang.demo_lehoang.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public List<TodoItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public List<TodoItem> getFilteredItems(String filter) {
        switch (filter) {
            case "active":
                return items.stream().filter(i -> !i.isCompleted()).collect(Collectors.toList());
            case "completed":
                return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
            default:
                return getAllItems();
        }
    }

    public void addItem(String title) {
        items.add(new TodoItem(idGenerator.incrementAndGet(), title));
    }

    public void toggleCompleted(Long id) {
        items.stream().filter(i -> i.getId().equals(id)).findFirst()
            .ifPresent(i -> i.setCompleted(!i.isCompleted()));
    }

    public void deleteItem(Long id) {
        items.removeIf(i -> i.getId().equals(id));
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

    public long countActive() {
        return items.stream().filter(i -> !i.isCompleted()).count();
    }
} 