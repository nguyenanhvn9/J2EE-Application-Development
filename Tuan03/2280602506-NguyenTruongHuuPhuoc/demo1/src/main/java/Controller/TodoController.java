package com.example.demo.controller;

import com.example.demo.model.TodoItem;
import com.example.demo.service.TodoService;
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
    public String showList(@RequestParam(name = "filter", required = false, defaultValue = "all") String filter,
                       Model model) {
        model.addAttribute("items", todoService.getFilteredItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("itemsLeft", todoService.countActiveItems());
        model.addAttribute("currentFilter", filter);
        return "index";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute("newTodo") TodoItem item) {
        todoService.addItem(item);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggleCompleted(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/todos";
    }
    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}
