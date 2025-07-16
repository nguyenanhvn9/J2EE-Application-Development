package com.hutech.cos141_demo.BaiTHTuan03.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hutech.cos141_demo.BaiTHTuan03.model.TodoItem;

@Service
public class TodoService {
    private List<TodoItem> items = new ArrayList<>();
    private Long nextId = 1L;

    public List<TodoItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public void addItem(String title) {
        TodoItem item = new TodoItem(nextId++, title);
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

    public int getActiveCount() {
        return (int) items.stream().filter(item -> !item.isCompleted()).count();
    }

    public List<TodoItem> getFilteredItems(String filter) {
        if (filter == null || filter.equals("all")) {
            return getAllItems();
        } else if (filter.equals("active")) {
            return items.stream().filter(item -> !item.isCompleted()).collect(Collectors.toList());
        } else if (filter.equals("completed")) {
            return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
        }
        return getAllItems();
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }
} 