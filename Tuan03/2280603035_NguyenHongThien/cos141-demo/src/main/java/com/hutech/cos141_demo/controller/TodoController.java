package com.hutech.cos141_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hutech.cos141_demo.model.TodoItem;
import com.hutech.cos141_demo.service.TodoService;

@Controller
@RequestMapping("/todos")
public class TodoController {
    
    @Autowired
    private TodoService todoService;

    // Hiển thị danh sách công việc
    @GetMapping
    public String listTodos(@RequestParam(required = false) String filter, Model model) {
        model.addAttribute("todos", todoService.getAllItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeCount", todoService.countActiveItems());
        model.addAttribute("filter", filter != null ? filter : "all");
        return "index";
    }

    // Thêm công việc mới
    @PostMapping("/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem todo) {
        todoService.addItem(todo);
        return "redirect:/todos";
    }

    // Đánh dấu hoàn thành/chưa hoàn thành
    @PostMapping("/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/todos";
    }

    // Xóa công việc
    @GetMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/todos";
    }

    // Xóa tất cả công việc đã hoàn thành
    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}
