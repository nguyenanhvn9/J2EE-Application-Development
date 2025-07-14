package com.example.todolistdemo.controller;

import com.example.todolistdemo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

   @GetMapping("/todos")
public String index(Model model, @RequestParam(required = false, defaultValue = "all") String filter) {
    model.addAttribute("todos", todoService.getAllItems(filter));
    model.addAttribute("newTodo", new com.example.todolistdemo.model.TodoItem());
    model.addAttribute("activeCount", todoService.countActiveItems());
    model.addAttribute("currentFilter", filter);
    return "index";

    }

    @PostMapping("/todos/add")
public String addTodo(@ModelAttribute("newTodo") com.example.todolistdemo.model.TodoItem todo) {
    todoService.addItem(todo.getTitle());
    return "redirect:/todos"; // Sửa tại đây
}

@PostMapping("/todos/{id}/toggle")
public String toggleTodo(@PathVariable Long id) {
    todoService.toggleCompleted(id);
    return "redirect:/todos"; // Sửa tại đây
}

@PostMapping("/todos/{id}/delete")
public String deleteTodo(@PathVariable Long id) {
    todoService.deleteItem(id);
    return "redirect:/todos"; // Sửa tại đây
}

@PostMapping("/todos/clear")
public String clearCompleted() {
    todoService.clearCompleted();
    return "redirect:/todos"; // Sửa tại đây
}

}
