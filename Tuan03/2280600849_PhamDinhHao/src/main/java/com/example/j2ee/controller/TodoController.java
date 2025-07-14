package com.example.j2ee.controller;

import com.example.j2ee.model.TodoItem;
import com.example.j2ee.service.TodoService;
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
        model.addAttribute("activeCount", todoService.getActiveCount());
        model.addAttribute("filter", filter);
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