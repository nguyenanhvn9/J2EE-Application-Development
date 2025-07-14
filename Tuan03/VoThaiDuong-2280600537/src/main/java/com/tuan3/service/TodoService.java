package com.tuan3.service;

import com.tuan3.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {
    private final List<TodoItem> todoList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<TodoItem> getAllItems() {
        return todoList;
    }

    public void addItem(String title) {
        TodoItem newItem = new TodoItem();
        newItem.setId(idCounter.getAndIncrement());
        newItem.setTitle(title);
        newItem.setCompleted(false);
        todoList.add(newItem);
    }

    public void toggleCompleted(Long id) {
        Optional<TodoItem> optionalItem = todoList.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        optionalItem.ifPresent(item -> item.setCompleted(!item.isCompleted()));
    }

    public void deleteItem(Long id) {
        todoList.removeIf(item -> item.getId().equals(id));
    }

    public void clearCompleted() {
        List<TodoItem> items = todoList.stream()
                .filter(item -> item.isCompleted() == true)
                .toList();

        todoList.removeAll(items);
    }
}
