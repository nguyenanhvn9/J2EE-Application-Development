package com.example.todolist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todolist.model.TodoItem;

@Service
public class TodoService {
    private final List<TodoItem> todoItems = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public TodoService() {
        // Initialize with some sample data
        addItem("Công việc 1");
        addItem("Đi chơi");
        addItem("Đi ngủ");
        toggleCompleted(2L); // Mark "Công việc 2" as completed
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
            TodoItem newItem = new TodoItem(idCounter.getAndIncrement(), title.trim());
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

    public void clearCompleted() {
        todoItems.removeIf(TodoItem::isCompleted);
    }

    public long getActiveItemsCount() {
        return todoItems.stream()
                .filter(item -> !item.isCompleted())
                .count();
    }

    public boolean hasCompletedItems() {
        return todoItems.stream()
                .anyMatch(TodoItem::isCompleted);
    }
}
