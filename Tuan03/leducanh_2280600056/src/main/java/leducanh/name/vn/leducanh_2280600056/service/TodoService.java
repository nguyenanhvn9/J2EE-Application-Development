package leducanh.name.vn.leducanh_2280600056.service;

import leducanh.name.vn.leducanh_2280600056.model.Todo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {

    private final List<Todo> todos = new ArrayList<>();
    private final int MAX_COMPLETED = 2;
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Todo> findAll() {
        return new ArrayList<>(todos);
    }

    public List<Todo> findByStatus(String filter) {
        return switch (filter) {
            case "active" -> todos.stream().filter(t -> !t.isCompleted()).toList();
            case "completed" -> todos.stream().filter(Todo::isCompleted).toList();
            default -> findAll();
        };
    }

    public List<Todo> search(String keyword) {
        return todos.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public Todo addTodo(String title) {
        Todo todo = new Todo(idCounter.getAndIncrement(), title, false);
        todos.add(todo);
        return todo;
    }

    public void markCompleted(Long id) {
        todos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .ifPresent(t -> t.setCompleted(true));
    }

    public boolean toggleCompleted(Long id) {
        Optional<Todo> opt = todos.stream().filter(t -> t.getId().equals(id)).findFirst();
        if (opt.isPresent()) {
            Todo todo = opt.get();
            if (!todo.isCompleted() && getCompletedCount() >= MAX_COMPLETED) {
                return false; // Không cho tick thêm nếu đã đủ 2
            }
            todo.setCompleted(!todo.isCompleted());
            return true;
        }
        return false;
    }

    public int getCompletedCount() {
        return (int) todos.stream().filter(Todo::isCompleted).count();
    }

    public int getRemainingCount() {
        return (int) todos.stream().filter(t -> !t.isCompleted()).count();
    }

    public void clearCompleted() {
        todos.removeIf(Todo::isCompleted);
    }

    public long countActive() {
        return todos.stream().filter(t -> !t.isCompleted()).count();
    }
}
