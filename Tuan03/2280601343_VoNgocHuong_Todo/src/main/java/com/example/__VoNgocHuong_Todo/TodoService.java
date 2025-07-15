package com.example.__VoNgocHuong_Todo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<TodoItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public void addItem(String title) {
        items.add(new TodoItem(idGenerator.getAndIncrement(), title));
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