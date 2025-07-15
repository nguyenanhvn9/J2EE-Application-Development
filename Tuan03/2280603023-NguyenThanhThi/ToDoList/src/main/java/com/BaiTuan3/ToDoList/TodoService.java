package com.BaiTuan3.ToDoList;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private List<TodoItem> items = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

    public List<TodoItem> getAllItems() {
        return items;
    }

    public void addItem(String title) {
        items.add(new TodoItem(idCounter.incrementAndGet(), title));
    }

    public void toggleCompleted(Long id) {
        for (TodoItem item : items) {
            if (item.getId().equals(id)) {
                item.setCompleted(!item.isCompleted());
                break;
            }
        }
    }

    public void deleteItem(Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public List<TodoItem> getFilteredItems(String filter) {
        if ("active".equals(filter)) {
            return items.stream().filter(i -> !i.isCompleted()).collect(Collectors.toList());
        } else if ("completed".equals(filter)) {
            return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
        }
        return items;
    }

    public long countActiveItems() {
        return items.stream().filter(i -> !i.isCompleted()).count();
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }
} 