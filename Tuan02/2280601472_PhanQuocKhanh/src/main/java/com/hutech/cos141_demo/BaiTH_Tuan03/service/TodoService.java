package com.hutech.cos141_demo.BaiTH_Tuan03.service;

import com.hutech.cos141_demo.BaiTH_Tuan03.model.TodoItem;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public List<TodoItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public void addItem(String title) {
        items.add(new TodoItem(idGen.getAndIncrement(), title));
    }

    public void toggleCompleted(Long id) {
        items.stream().filter(i -> i.getId().equals(id)).findFirst()
                .ifPresent(i -> i.setCompleted(!i.isCompleted()));
    }

    public void deleteItem(Long id) {
        items.removeIf(i -> i.getId().equals(id));
    }

    public long getActiveCount() {
        return items.stream().filter(i -> !i.isCompleted()).count();
    }

    public List<TodoItem> getFilteredItems(String filter) {
        if (filter == null || filter.equals("all")) return getAllItems();
        if (filter.equals("active")) return items.stream().filter(i -> !i.isCompleted()).collect(Collectors.toList());
        if (filter.equals("completed")) return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
        return getAllItems();
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }
} 