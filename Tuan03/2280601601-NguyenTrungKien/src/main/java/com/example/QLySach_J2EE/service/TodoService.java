package com.example.QLySach_J2EE.service;

import com.example.QLySach_J2EE.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private List<TodoItem> todoItems = new ArrayList<>();
    private Long nextId = 1L;

    public TodoService() {
        // Initialize with sample todos safely
        try {
            addItem("Học Spring Boot");
            addItem("Làm bài tập Thymeleaf");
            addItem("Ôn tập J2EE");
        } catch (Exception e) {
            // If initialization fails, start with empty list
            todoItems = new ArrayList<>();
            nextId = 1L;
        }
    }

    public List<TodoItem> getAllItems() {
        if (todoItems == null) {
            todoItems = new ArrayList<>();
        }
        return new ArrayList<>(todoItems);
    }

    public List<TodoItem> getItemsByFilter(String filter) {
        try {
            if (todoItems == null) {
                todoItems = new ArrayList<>();
            }
            if (filter == null || filter.isEmpty() || "all".equals(filter)) {
                return getAllItems();
            } else if ("active".equals(filter)) {
                return todoItems.stream()
                        .filter(item -> item != null && !item.isCompleted())
                        .collect(Collectors.toList());
            } else if ("completed".equals(filter)) {
                return todoItems.stream()
                        .filter(item -> item != null && item.isCompleted())
                        .collect(Collectors.toList());
            }
            return getAllItems();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void addItem(String title) {
        if (title != null && !title.trim().isEmpty()) {
            TodoItem item = new TodoItem(title.trim());
            item.setId(nextId++);
            if (todoItems == null) {
                todoItems = new ArrayList<>();
            }
            todoItems.add(item);
        }
    }

    public void addItem(TodoItem item) {
        if (item != null && item.getTitle() != null && !item.getTitle().trim().isEmpty()) {
            item.setId(nextId++);
            if (todoItems == null) {
                todoItems = new ArrayList<>();
            }
            todoItems.add(item);
        }
    }

    public void toggleCompleted(Long id) {
        if (id != null && todoItems != null) {
            todoItems.stream()
                    .filter(item -> item != null && id.equals(item.getId()))
                    .findFirst()
                    .ifPresent(item -> item.setCompleted(!item.isCompleted()));
        }
    }

    public void deleteItem(Long id) {
        if (id != null && todoItems != null) {
            todoItems.removeIf(item -> item != null && id.equals(item.getId()));
        }
    }

    public void clearCompleted() {
        if (todoItems != null) {
            todoItems.removeIf(item -> item != null && item.isCompleted());
        }
    }

    public long getActiveCount() {
        try {
            if (todoItems == null) {
                return 0;
            }
            return todoItems.stream()
                    .filter(item -> item != null && !item.isCompleted())
                    .count();
        } catch (Exception e) {
            return 0;
        }
    }

    public long getCompletedCount() {
        try {
            if (todoItems == null) {
                return 0;
            }
            return todoItems.stream()
                    .filter(item -> item != null && item.isCompleted())
                    .count();
        } catch (Exception e) {
            return 0;
        }
    }

    public long getTotalCount() {
        try {
            if (todoItems == null) {
                return 0;
            }
            return todoItems.size();
        } catch (Exception e) {
            return 0;
        }
    }
} 