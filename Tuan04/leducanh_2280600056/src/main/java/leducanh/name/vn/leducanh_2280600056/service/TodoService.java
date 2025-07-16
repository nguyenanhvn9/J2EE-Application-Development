package leducanh.name.vn.leducanh_2280600056.service;

import leducanh.name.vn.leducanh_2280600056.model.Todo;
import leducanh.name.vn.leducanh_2280600056.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final int MAX_COMPLETED = 2;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public List<Todo> findByStatus(String filter) {
        return switch (filter) {
            case "active" -> todoRepository.findByCompleted(false);
            case "completed" -> todoRepository.findByCompleted(true);
            default -> findAll();
        };
    }

    public List<Todo> search(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Todo addTodo(String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    public boolean toggleCompleted(Long id) {
        return todoRepository.findById(id).map(todo -> {
            if (!todo.isCompleted() && getCompletedCount() >= MAX_COMPLETED) {
                return false;
            }
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
            return true;
        }).orElse(false);
    }

    public int getCompletedCount() {
        return (int) todoRepository.findByCompleted(true).size();
    }

    public int getRemainingCount() {
        return (int) todoRepository.findByCompleted(false).size();
    }

    public long countActive() {
        return todoRepository.findByCompleted(false).size();
    }

    public void clearCompleted() {
        List<Todo> completedTodos = todoRepository.findByCompleted(true);
        todoRepository.deleteAll(completedTodos);
    }
}
