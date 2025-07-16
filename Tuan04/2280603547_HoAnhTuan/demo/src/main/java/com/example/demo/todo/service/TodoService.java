package com.example.demo.todo.service;

import com.example.demo.todo.model.TodoItem;
import com.example.demo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public List<TodoItem> getAllItems(String filter) {
        if ("active".equalsIgnoreCase(filter)) {
            return todoRepository.findByCompleted(false);
        } else if ("completed".equalsIgnoreCase(filter)) {
            return todoRepository.findByCompleted(true);
        } else {
            return todoRepository.findAll();
        }
    }

    public void addItem(TodoItem todoItem) {
        todoItem.setCompleted(false);
        todoRepository.save(todoItem);
    }

    public void toggleCompleted(Long id) {
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("TodoItem with ID " + id + " does not exist."));
        todoItem.setCompleted(!todoItem.isCompleted());
        todoRepository.save(todoItem);
    }

    public void deleteItem(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalStateException("TodoItem with ID " + id + " does not exist.");
        }
        todoRepository.deleteById(id);
    }

    public void clearCompleted() {
        todoRepository.deleteAll(todoRepository.findByCompleted(true));
    }

    public long getActiveItemsCount() {
        return todoRepository.findByCompleted(false).size();
    }
}