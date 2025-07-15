package com.example.ToDoList.Controllers;

import com.example.ToDoList.Services.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
                        Model model) {
        model.addAttribute("todoItems", todoService.getAllItems(filter));
        model.addAttribute("newTodo", new NewTodo());
        model.addAttribute("itemsLeft", todoService.countActiveItems());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute NewTodo newTodo) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/todos";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }

    public static class NewTodo {
        private String title;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }
}
