package com.example.todoapp.controller;

import com.example.todoapp.model.TodoItem;
import com.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
        model.addAttribute("items", todoService.filter(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeCount", todoService.countActive());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("newTodo") TodoItem newTodo) {
        todoService.add(newTodo.getTitle());
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        todoService.toggle(id);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/todos";
    }

    @PostMapping("/clear")
    public String clear() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}
