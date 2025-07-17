package com.example.todolist.service;

import com.example.todolist.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final List<TodoItem> todoList = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public List<TodoItem> getAllItems(String filter) {
        if (filter == null) {
            return new ArrayList<>(todoList);
        }
        return switch (filter) {
            case "active" -> todoList.stream().filter(i -> !i.isCompleted()).toList();
            case "completed" -> todoList.stream().filter(TodoItem::isCompleted).toList();
            default -> new ArrayList<>(todoList);
        };
    }

    public void addItem(String title) {
        todoList.add(new TodoItem(idGenerator.incrementAndGet(), title));
    }

    public void toggleCompleted(Long id) {
        todoList.stream().filter(i -> i.getId().equals(id)).findFirst()
            .ifPresent(i -> i.setCompleted(!i.isCompleted()));
    }

    public void deleteItem(Long id) {
        todoList.removeIf(i -> i.getId().equals(id));
    }

    public void clearCompleted() {
        todoList.removeIf(TodoItem::isCompleted);
    }

    public long countActiveItems() {
        return todoList.stream().filter(i -> !i.isCompleted()).count();
    }
}
