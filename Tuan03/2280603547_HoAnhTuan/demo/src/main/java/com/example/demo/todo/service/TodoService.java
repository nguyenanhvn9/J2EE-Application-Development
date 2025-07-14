package com.example.demo.todo.service;

import com.example.demo.todo.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final List<TodoItem> items = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idCounter = new AtomicLong(0);

    public List<TodoItem> getAllItems(String filter) {
        synchronized (items) {
            List<TodoItem> result = new ArrayList<>(items);
            if (filter == null || filter.isEmpty() || filter.equals("all")) {
                return result;
            } else if (filter.equals("active")) {
                return result.stream()
                        .filter(item -> !item.isCompleted())
                        .toList();
            } else if (filter.equals("completed")) {
                return result.stream()
                        .filter(TodoItem::isCompleted)
                        .toList();
            }
            return result;
        }
    }

    public void addItem(TodoItem item) {
        synchronized (items) {
            item.setId(idCounter.incrementAndGet());
            items.add(item);
        }
    }

    public void toggleCompleted(Long id) {
        synchronized (items) {
            Optional<TodoItem> item = items.stream()
                    .filter(i -> i.getId().equals(id))
                    .findFirst();
            item.ifPresent(i -> i.setCompleted(!i.isCompleted()));
        }
    }

    public void deleteItem(Long id) {
        synchronized (items) {
            items.removeIf(i -> i.getId().equals(id));
        }
    }

    public long getActiveItemCount() {
        synchronized (items) {
            return items.stream().filter(i -> !i.isCompleted()).count();
        }
    }

    public void clearCompleted() {
        synchronized (items) {
            items.removeIf(TodoItem::isCompleted);
        }
    }
}