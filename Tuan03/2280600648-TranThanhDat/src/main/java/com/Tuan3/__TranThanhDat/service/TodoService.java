package com.Tuan3.__TranThanhDat.service;

import com.Tuan3.__TranThanhDat.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private List<TodoItem> items = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

public List<TodoItem> getAllItems(String filter) {
    if (filter == null) {
        filter = "all";
    }

    return switch (filter) {
        case "active" -> items.stream().filter(i -> !i.isCompleted()).toList();
        case "completed" -> items.stream().filter(TodoItem::isCompleted).toList();
        default -> new ArrayList<>(items); // "all" hoặc không hợp lệ
    };
}


    public void addItem(String title) {
        items.add(new TodoItem(idCounter.incrementAndGet(), title));
    }

    public void toggleCompleted(Long id) {
        items.stream()
             .filter(i -> i.getId().equals(id))
             .findFirst()
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
