package org.example.booking.service;

import org.example.booking.model.Todo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final Map<Long, Todo> store = new LinkedHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Todo> getAll() {
        return new ArrayList<>(store.values());
    }

    public List<Todo> getFiltered(String filter) {
        return switch (filter) {
            case "active" -> store.values().stream().filter(t -> !t.isCompleted()).toList();
            case "completed" -> store.values().stream().filter(Todo::isCompleted).toList();
            default -> getAll();
        };
    }

    public Todo add(String title) {
        Todo todo = new Todo(idCounter.getAndIncrement(), title, false);
        store.put(todo.getId(), todo);
        return todo;
    }

    public void toggle(Long id) {
        Todo todo = store.get(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
        }
    }

    public void delete(Long id) {
        store.remove(id);
    }

    public void toggleAll(boolean completed) {
        store.values().forEach(t -> t.setCompleted(completed));
    }

    public void clearCompleted() {
        store.values().removeIf(Todo::isCompleted);
    }
}
