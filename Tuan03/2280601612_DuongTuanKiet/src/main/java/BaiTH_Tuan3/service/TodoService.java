package BaiTH_Tuan3.service;

import BaiTH_Tuan3.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> todoList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<TodoItem> getAllItems(String filter) {
        if (filter == null) filter = ""; // 👈 Bắt buộc thêm dòng này để tránh lỗi

        return switch (filter) {
            case "active" -> todoList.stream().filter(item -> !item.isCompleted()).toList();
            case "completed" -> todoList.stream().filter(TodoItem::isCompleted).toList();
            default -> new ArrayList<>(todoList);
        };
    }


    public void addItem(String title) {
        todoList.add(new TodoItem(counter.incrementAndGet(), title));
    }

    public void toggleCompleted(Long id) {
        todoList.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .ifPresent(item -> item.setCompleted(!item.isCompleted()));
    }

    public void deleteItem(Long id) {
        todoList.removeIf(item -> item.getId().equals(id));
    }

    public void clearCompleted() {
        todoList.removeIf(TodoItem::isCompleted);
    }

    public long countActiveItems() {
        return todoList.stream().filter(item -> !item.isCompleted()).count();
    }
}