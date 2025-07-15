package com.example.ToDoList.Services;

import com.example.ToDoList.Models.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private List<TodoItem> todoList = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

    public List<TodoItem> getAllItems(String filter) {
        return switch (filter) {
            case "active" -> todoList.stream().filter(t -> !t.isCompleted()).toList();
            case "completed" -> todoList.stream().filter(TodoItem::isCompleted).toList();
            default -> new ArrayList<>(todoList);
        };
    }

    public void addItem(String title) {
        todoList.add(new TodoItem(idCounter.incrementAndGet(), title));
    }

    public void toggleCompleted(Long id) {
        for (TodoItem item : todoList) {
            if (item.getId().equals(id)) {
                item.setCompleted(!item.isCompleted());
                break;
            }
        }
    }
    public int countActiveItems() {
        return (int) todoList.stream()
                .filter(item -> !item.isCompleted())
                .count();
    }



    public void deleteItem(Long id) {
        todoList.removeIf(item -> item.getId().equals(id));
    }

    public void clearCompleted() {
        todoList.removeIf(TodoItem::isCompleted);
    }

    public long countActive() {
        return todoList.stream().filter(t -> !t.isCompleted()).count();
    }
}
