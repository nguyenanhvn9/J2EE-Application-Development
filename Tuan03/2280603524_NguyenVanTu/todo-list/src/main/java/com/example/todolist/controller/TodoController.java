package com.example.todolist.controller;

import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String filter) {
        model.addAttribute("todos", todoService.getAllItems(filter));
        model.addAttribute("newTodo", "");
        model.addAttribute("itemsLeft", todoService.countActiveItems());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") String title, @RequestParam(required = false) String filter) {
        if (!title.trim().isEmpty()) {
            todoService.addItem(title.trim());
        }
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.toggleCompleted(id);
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.deleteItem(id);
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted(@RequestParam(required = false) String filter) {
        todoService.clearCompleted();
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }
}
