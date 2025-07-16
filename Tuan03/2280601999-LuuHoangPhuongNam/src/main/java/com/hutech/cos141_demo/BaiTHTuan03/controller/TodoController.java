package com.hutech.cos141_demo.BaiTHTuan03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hutech.cos141_demo.BaiTHTuan03.model.TodoItem;
import com.hutech.cos141_demo.BaiTHTuan03.service.TodoService;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter, Model model) {
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("todos", todoService.getFilteredItems(filter));
        model.addAttribute("activeCount", todoService.getActiveCount());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("newTodo") TodoItem newTodo) {
        if (newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
            todoService.addItem(newTodo.getTitle());
        }
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, @RequestParam(value = "filter", required = false, defaultValue = "all") String filter) {
        todoService.toggleCompleted(id);
        return "redirect:/todos?filter=" + filter;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @RequestParam(value = "filter", required = false, defaultValue = "all") String filter) {
        todoService.deleteItem(id);
        return "redirect:/todos?filter=" + filter;
    }

    @PostMapping("/clear-completed")
    public String clearCompleted(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter) {
        todoService.clearCompleted();
        return "redirect:/todos?filter=" + filter;
    }
} 