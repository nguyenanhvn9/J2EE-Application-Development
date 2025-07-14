package com.tuan3.controller;

import com.tuan3.model.TodoItem;
import com.tuan3.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
// @RequestMapping("/")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(@RequestParam(required = false, value = "filter", defaultValue = "all") String filter,
            Model model) {

        if (filter.equals("active")) {
            model.addAttribute("todoList",
                    todoService.getAllItems().stream().filter(item -> item.isCompleted() == false));
        } else if (filter.equals("completed")) {
            model.addAttribute("todoList",
                    todoService.getAllItems().stream().filter(item -> item.isCompleted() == true));
        } else {
            model.addAttribute("todoList", todoService.getAllItems());
        }

        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("itemsLeft",
                todoService.getAllItems().stream().filter(item -> item.isCompleted() == false).count());
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/";
    }

    @PostMapping("/todos")
    public String addTodo(@RequestParam String title) {
        todoService.addItem(title);
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

    @PostMapping("todos/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/";
    }
}
