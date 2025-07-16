package com.example.__NguyenNhatNam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import  com.example.__NguyenNhatNam.model.TodoItem;

@Service
public class TodoService {
    private List<TodoItem> items = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong(1);

    public List<TodoItem> getAllItems(String filter) {
        switch (filter) {
            case "active":
                return items.stream().filter(item -> !item.isCompleted()).collect(Collectors.toList());
            case "completed":
                return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
            default:
                return items;
        }
    }

    public void addItem(String title) {
        items.add(new TodoItem(nextId.getAndIncrement(), title));
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

    public long countActiveItems() {
        return items.stream().filter(item -> !item.isCompleted()).count();
    }
}