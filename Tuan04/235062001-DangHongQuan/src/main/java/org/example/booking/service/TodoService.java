package org.example.booking.service;

import org.example.booking.Response.TodoRepository;
import org.example.booking.model.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public List<Todo> getFiltered(String filter) {
        return switch (filter) {
            case "active" -> todoRepository.findAll().stream().filter(t -> !t.isCompleted()).toList();
            case "completed" -> todoRepository.findAll().stream().filter(Todo::isCompleted).toList();
            default -> getAll();
        };
    }

    public Todo add(String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    public void toggle(Long id) {
        todoRepository.findById(id).ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        });
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    public void toggleAll(boolean completed) {
        List<Todo> todos = todoRepository.findAll();
        for (Todo todo : todos) {
            todo.setCompleted(completed);
        }
        todoRepository.saveAll(todos);
    }

    public void clearCompleted() {
        List<Todo> completedTodos = todoRepository.findAll().stream()
                .filter(Todo::isCompleted)
                .toList();
        todoRepository.deleteAll(completedTodos);
    }
}
