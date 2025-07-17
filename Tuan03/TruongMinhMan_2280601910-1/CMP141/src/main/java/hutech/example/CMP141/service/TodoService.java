package hutech.example.CMP141.service;

import hutech.example.CMP141.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final List<TodoItem> items = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<TodoItem> getAllItems() {
        return new ArrayList<>(items);
    }

    public void addItem(String title) {
        items.add(new TodoItem(nextId.getAndIncrement(), title, false));
    }

    public void toggleCompleted(Long id) {
        items.stream().filter(item -> item.getId().equals(id)).findFirst()
            .ifPresent(item -> item.setCompleted(!item.isCompleted()));
    }

    public void deleteItem(Long id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public long getActiveCount() {
        return items.stream().filter(item -> !item.isCompleted()).count();
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

    public List<TodoItem> getFilteredItems(String filter) {
        if (filter == null || filter.equals("all")) {
            return getAllItems();
        } else if (filter.equals("active")) {
            return items.stream().filter(item -> !item.isCompleted()).collect(Collectors.toList());
        } else if (filter.equals("completed")) {
            return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
        }
        return getAllItems();
    }
} 