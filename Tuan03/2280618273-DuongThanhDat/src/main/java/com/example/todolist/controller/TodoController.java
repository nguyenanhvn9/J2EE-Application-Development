package com.example.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todolist.model.TodoItem;
import com.example.todolist.service.TodoService;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
                       Model model) {
        model.addAttribute("todos", todoService.getFilteredItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeItemsCount", todoService.getActiveItemsCount());
        model.addAttribute("hasCompletedItems", todoService.hasCompletedItems());
        model.addAttribute("currentFilter", filter);
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute TodoItem newTodo) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
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
