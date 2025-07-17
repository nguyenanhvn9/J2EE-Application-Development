package hutech.example.CMP141.controller;

import hutech.example.CMP141.model.TodoItem;
import hutech.example.CMP141.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping({"/", "/todos"})
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("todos", todoService.getFilteredItems(filter));
        model.addAttribute("activeCount", todoService.getActiveCount());
        model.addAttribute("filter", filter == null ? "all" : filter);
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) {
        if (newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
            todoService.addItem(newTodo.getTitle().trim());
        }
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleCompleted(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.toggleCompleted(id);
        return filter != null ? "redirect:/?filter=" + filter : "redirect:/";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.deleteItem(id);
        return filter != null ? "redirect:/?filter=" + filter : "redirect:/";
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted(@RequestParam(value = "filter", required = false) String filter) {
        todoService.clearCompleted();
        return filter != null ? "redirect:/?filter=" + filter : "redirect:/";
    }
} 