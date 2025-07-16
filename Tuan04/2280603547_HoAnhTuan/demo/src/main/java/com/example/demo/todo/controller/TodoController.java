package com.example.demo.todo.controller;

import com.example.demo.todo.model.TodoItem;
import com.example.demo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/", "/index"})
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public String listTodos(@RequestParam(required = false) String filter, Model model) {
        model.addAttribute("todos", todoService.getAllItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeItemsCount", todoService.getActiveItemsCount());
        model.addAttribute("filter", filter != null ? filter : "all");
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@Valid @ModelAttribute("newTodo") TodoItem todoItem, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("todos", todoService.getAllItems(null));
            model.addAttribute("activeItemsCount", todoService.getActiveItemsCount());
            model.addAttribute("filter", "all");
            return "index";
        }
        todoService.addItem(todoItem);
        return "redirect:/";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/";
    }
}