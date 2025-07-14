package leducanh.name.vn.leducanh_2280600056.controller;

import leducanh.name.vn.leducanh_2280600056.model.Todo;
import leducanh.name.vn.leducanh_2280600056.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoViewController {

    private final TodoService todoService;

    @Autowired
    public TodoViewController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String listTodos(
            @RequestParam(defaultValue = "all") String filter,
            @RequestParam(required = false) String search,
            Model model
    ) {
        List<Todo> todos;

        if (search != null && !search.isEmpty()) {
            todos = todoService.search(search);
        } else {
            todos = todoService.findByStatus(filter);
        }

        model.addAttribute("todos", todos);
        model.addAttribute("filter", filter);
        model.addAttribute("activeCount", todoService.countActive());

        return "todos"; // => src/main/resources/templates/todos.html
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        todoService.addTodo(title);
        return "redirect:/todos";
    }

    @PostMapping("/complete/{id}")
    public String completeTodo(@PathVariable Long id) {
        todoService.markCompleted(id);
        return "redirect:/todos";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}
