package com.hutech.BuiMinhTan_2280602856.controller;

import com.hutech.BuiMinhTan_2280602856.model.TodoItem;
import com.hutech.BuiMinhTan_2280602856.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String filter, Model model) {
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("todoItems", todoService.getFilteredItems(filter));
        model.addAttribute("activeItemsCount", todoService.getActiveItemsCount());
        model.addAttribute("currentFilter", filter != null ? filter : "all");
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute TodoItem newTodo) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.toggleCompleted(id);
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        todoService.deleteItem(id);
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted(@RequestParam(required = false) String filter) {
        todoService.clearCompleted();
        return "redirect:/" + (filter != null ? "?filter=" + filter : "");
    }
}
