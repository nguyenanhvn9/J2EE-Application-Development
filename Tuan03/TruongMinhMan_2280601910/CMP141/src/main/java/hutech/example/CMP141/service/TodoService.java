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

    public List<TodoItem> getAllItems(String filter) {
        if (filter == null || filter.equals("all")) {
            return new ArrayList<>(items);
        } else if (filter.equals("active")) {
            return items.stream().filter(i -> !i.isCompleted()).collect(Collectors.toList());
        } else if (filter.equals("completed")) {
            return items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList());
        }
        return new ArrayList<>(items);
    }

    public void addItem(String title) {
        items.add(0, new TodoItem(nextId.getAndIncrement(), title, false));
    }

    public void toggleCompleted(Long id) {
        items.stream().filter(i -> i.getId().equals(id)).findFirst()
            .ifPresent(i -> i.setCompleted(!i.isCompleted()));
    }

    public void deleteItem(Long id) {
        items.removeIf(i -> i.getId().equals(id));
    }

    public void clearCompleted() {
        items.removeIf(TodoItem::isCompleted);
    }

    public long countActive() {
        return items.stream().filter(i -> !i.isCompleted()).count();
    }
} 