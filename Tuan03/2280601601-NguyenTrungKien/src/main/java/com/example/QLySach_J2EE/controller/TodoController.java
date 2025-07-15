package com.example.QLySach_J2EE.controller;

import com.example.QLySach_J2EE.model.TodoItem;
import com.example.QLySach_J2EE.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TodoController {
    
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            System.out.println("Loading index page...");
            List<TodoItem> items = todoService.getAllItems();
            model.addAttribute("todoItems", items);
            model.addAttribute("newTodo", new TodoItem());
            model.addAttribute("totalCount", items.size());
            model.addAttribute("activeCount", items.stream().filter(item -> !item.isCompleted()).count());
            model.addAttribute("completedCount", items.stream().filter(TodoItem::isCompleted).count());
            System.out.println("Index page loaded successfully with " + items.size() + " items");
            return "index";
        } catch (Exception e) {
            System.err.println("Error loading index page: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi tải danh sách công việc: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) {
        try {
            if (newTodo != null && newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
                todoService.addItem(newTodo.getTitle().trim());
            }
        } catch (Exception e) {
            // Log error but don't crash
        }
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleCompleted(@PathVariable Long id) {
        try {
            todoService.toggleCompleted(id);
        } catch (Exception e) {
            // Log error but don't crash
        }
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteItem(id);
        } catch (Exception e) {
            // Log error but don't crash
        }
        return "redirect:/";
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted() {
        try {
            todoService.clearCompleted();
        } catch (Exception e) {
            // Log error but don't crash
        }
        return "redirect:/";
    }
} 