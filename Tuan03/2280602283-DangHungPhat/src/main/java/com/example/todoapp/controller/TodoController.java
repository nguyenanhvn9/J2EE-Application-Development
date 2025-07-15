package com.example.todoapp.controller;

import com.example.todoapp.model.TodoItem;
import com.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
        List<TodoItem> todoList;

        if (filter == null || filter.isEmpty()) {
            todoList = todoService.getAllItems();
        } else {
            todoList = todoService.getFiltered(filter);
        }

        model.addAttribute("todoList", todoList);
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("filter", filter != null ? filter : "all");

        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TodoItem newTodo) {
        todoService.addItem(newTodo);
        return "redirect:/";
    }

    @PostMapping("/toggle/{id}")
    public String toggle(@PathVariable("id") long id) {
        todoService.toggleItem(id);
        return "redirect:/";
    }

    // ✅ ĐÃ SỬA: từ GET thành POST
    @PostMapping("/clear")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/";
    }
}
