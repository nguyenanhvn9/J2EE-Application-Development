package com.example.demo.controller;

import com.example.demo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.TodoItem;

@Controller
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String filter, Model model) {
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("todos", todoService.getFilteredItems(filter));
        model.addAttribute("itemsLeft", todoService.countUncompleted());
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute TodoItem newTodo) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.toggleCompleted(id);
        return "redirect:/?filter=" + (filter != null ? filter : "all");
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.deleteItem(id);
        return "redirect:/?filter=" + (filter != null ? filter : "all");
    }

    @PostMapping("/todos/clear")
    public String clearCompleted(@RequestParam(required = false) String filter) {
        todoService.clearCompleted();
        return "redirect:/?filter=" + (filter != null ? filter : "all");
    }
}

