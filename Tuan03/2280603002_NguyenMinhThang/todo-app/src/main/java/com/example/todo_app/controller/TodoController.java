package com.example.todo_app.controller;

import com.example.todo_app.model.TodoItem;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
        
        // Thêm một số dữ liệu mẫu
        todoService.addItem("Công việc 1");
        todoService.addItem("Công việc 2");
        todoService.addItem("Đi chơi");
        todoService.addItem("Đi ngủ");
        
        // Đánh dấu công việc 2 là đã hoàn thành
        todoService.toggleCompleted(2L);
    }

    @GetMapping
    public String index(Model model, @RequestParam(required = false) String filter) {
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("todos", todoService.getFilteredItems(filter));
        model.addAttribute("itemsLeft", todoService.countActiveItems());
        model.addAttribute("currentFilter", filter != null ? filter : "all");
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem todoItem) {
        if (todoItem != null && todoItem.getTitle() != null && !todoItem.getTitle().trim().isEmpty()) {
            todoService.addItem(todoItem.getTitle());
        }
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.toggleCompleted(id);
        return "redirect:/todos" + (filter != null ? "?filter=" + filter : "");
    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.deleteItem(id);
        return "redirect:/todos" + (filter != null ? "?filter=" + filter : "");
    }
    
    @PostMapping("/clear-completed")
    public String clearCompleted(@RequestParam(required = false) String filter) {
        todoService.clearCompleted();
        return "redirect:/todos" + (filter != null ? "?filter=" + filter : "");
    }
}