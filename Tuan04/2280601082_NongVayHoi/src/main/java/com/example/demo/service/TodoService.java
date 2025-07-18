package com.example.demo.service;

import com.example.demo.model.TodoItem;
import com.example.demo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepo;

    public TodoService(TodoRepository todoRepo) {
        this.todoRepo = todoRepo;
    }

    // Thêm mục mới
    public void addItem(String title) {
        TodoItem item = new TodoItem();
        item.setTitle(title);
        item.setCompleted(false);
        todoRepo.save(item);
    }

    // Lọc theo trạng thái
    public List<TodoItem> getFilteredItems(String filter) {
        if ("completed".equalsIgnoreCase(filter)) {
            return todoRepo.findByCompleted(true);
        } else if ("active".equalsIgnoreCase(filter)) {
            return todoRepo.findByCompleted(false);
        }
        return todoRepo.findAll();
    }

    // Đổi trạng thái completed
    public void toggleCompleted(UUID id) {
        todoRepo.findById(id).ifPresent(item -> {
            item.setCompleted(!item.isCompleted());
            todoRepo.save(item);
        });
    }

    // Xoá 1 mục
    public void deleteItem(UUID id) {
        todoRepo.deleteById(id);
    }

    // Xoá tất cả mục đã hoàn thành
    public void clearCompleted() {
        List<TodoItem> completed = todoRepo.findByCompleted(true);
        todoRepo.deleteAll(completed);
    }

    // Đếm số mục chưa hoàn thành
    public long countUncompleted() {
        return todoRepo.findByCompleted(false).size();
    }
}
