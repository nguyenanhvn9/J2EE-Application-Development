package com.hutech.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("")
    public String listTodos(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter, Model model) {
        model.addAttribute("todos", todoService.filterItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeCount", todoService.getActiveCount());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) {
        if (newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
            todoService.addItem(newTodo.getTitle().trim());
        }
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggleCompleted(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.toggleCompleted(id);
        return filter != null ? "redirect:/todos?filter=" + filter : "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.deleteItem(id);
        return filter != null ? "redirect:/todos?filter=" + filter : "redirect:/todos";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted(@RequestParam(value = "filter", required = false) String filter) {
        todoService.clearCompleted();
        return filter != null ? "redirect:/todos?filter=" + filter : "redirect:/todos";
    }
} 