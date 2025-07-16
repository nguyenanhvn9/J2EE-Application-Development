package com.example.__NguyenNhatNam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.__NguyenNhatNam.model.TodoItem;
import com.example.__NguyenNhatNam.service.TodoService;

@Controller
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
                        Model model) {
        model.addAttribute("items", todoService.getAllItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("itemsLeft", todoService.countActiveItems());
        model.addAttribute("currentFilter", filter);
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) {
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

    @PostMapping("/todos/clear")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/";
    }
}