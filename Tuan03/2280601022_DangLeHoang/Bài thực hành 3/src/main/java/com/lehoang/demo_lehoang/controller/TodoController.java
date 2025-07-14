package com.lehoang.demo_lehoang.controller;

import com.lehoang.demo_lehoang.service.TodoService;
import com.lehoang.demo_lehoang.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter, Model model) {
        model.addAttribute("todoItems", todoService.getFilteredItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeCount", todoService.countActive());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) {
        if (newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
            todoService.addItem(newTodo.getTitle());
        }
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleCompleted(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/";
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/";
    }
} 