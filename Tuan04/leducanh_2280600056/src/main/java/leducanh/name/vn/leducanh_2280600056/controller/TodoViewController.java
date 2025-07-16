package leducanh.name.vn.leducanh_2280600056.controller;

import leducanh.name.vn.leducanh_2280600056.model.Todo;
import leducanh.name.vn.leducanh_2280600056.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            @RequestParam(value = "error", required = false) String error,
            Model model) {

        List<Todo> todos = (search != null && !search.isEmpty())
                ? todoService.search(search)
                : todoService.findByStatus(filter);

        model.addAttribute("todos", todos);
        model.addAttribute("filter", filter);
        model.addAttribute("activeCount", todoService.countActive());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("maxCompleted", 2);

        // Chỉ add nếu error có giá trị
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }

        return "todos";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        todoService.addTodo(title);
        return "redirect:/todos";
    }

    @PostMapping("/complete/{id}")
    public String completeTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean success = todoService.toggleCompleted(id);
        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Chỉ được hoàn thành tối đa 2 công việc.");
        }
        return "redirect:/todos";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}
