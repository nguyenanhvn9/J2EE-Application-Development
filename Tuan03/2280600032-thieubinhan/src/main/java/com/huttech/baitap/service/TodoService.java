package com.huttech.baitap.service;

import com.huttech.baitap.model.Todoltem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service // [cite: 8]
public class TodoService {
    private final List<Todoltem> todoItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public TodoService() {
        // Add some initial items for testing
        addItem("Công việc 1");
        addItem("Công việc 2");
        addItem("Đi chơi");
        addItem("Đi ngủ");
    }

    public List<Todoltem> getAllItems() { // [cite: 9]
        return new ArrayList<>(todoItems);
    }

    public List<Todoltem> getFilteredItems(String filter) {
        if (filter == null || filter.isEmpty() || "all".equalsIgnoreCase(filter)) { // [cite: 30]
            return getAllItems();
        } else if ("active".equalsIgnoreCase(filter)) { // [cite: 30]
            return todoItems.stream()
                    .filter(item -> !item.isCompleted())
                    .collect(Collectors.toList());
        } else if ("completed".equalsIgnoreCase(filter)) { // [cite: 30]
            return todoItems.stream()
                    .filter(Todoltem::isCompleted)
                    .collect(Collectors.toList());
        }
        return getAllItems();
    }

    public void addItem(String title) { // [cite: 9]
        if (title != null && !title.trim().isEmpty()) {
            todoItems.add(new Todoltem(counter.incrementAndGet(), title));
        }
    }

    public void toggleCompleted(Long id) { // [cite: 9]
        todoItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setCompleted(!item.isCompleted())); // [cite: 19]
    }

    public void deleteItem(Long id) { // [cite: 9]
        todoItems.removeIf(item -> item.getId().equals(id)); // [cite: 24]
    }

    public int getActiveItemCount() { // [cite: 26]
        return (int) todoItems.stream()
                .filter(item -> !item.isCompleted())
                .count();
    }

    public void clearCompletedItems() {
        todoItems.removeIf(Todoltem::isCompleted);
    }
}