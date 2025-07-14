package com.example.todolistdemo.service;

import com.example.todolistdemo.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    // Lấy danh sách công việc dựa theo bộ lọc
    public List<TodoItem> getAllItems(String filter) {
        switch (filter) {
            case "active":
                return items.stream()
                        .filter(i -> !i.isCompleted())
                        .collect(Collectors.toList()); // dùng collect() thay vì toList()
            case "completed":
                return items.stream()
                        .filter(TodoItem::isCompleted)
                        .collect(Collectors.toList()); // dùng collect() thay vì toList()
            default:
                return new ArrayList<>(items); // trả về bản sao danh sách gốc
        }
    }

    // Thêm công việc mới
    public void addItem(String title) {
        items.add(new TodoItem(idCounter.incrementAndGet(), title));
    }

    // Đảo trạng thái hoàn thành
    public void toggleCompleted(Long id) {
        items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .ifPresent(i -> i.setCompleted(!i.isCompleted()));
    }

    // Xóa công việc theo id
    public void deleteItem(Long id) {
        items.removeIf(i -> i.getId().equals(id));
    }

    // Xóa tất cả công việc đã hoàn thành
    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

    // Đếm số lượng công việc chưa hoàn thành
    public long countActiveItems() {
        return items.stream()
                .filter(i -> !i.isCompleted())
                .count();
    }
}
