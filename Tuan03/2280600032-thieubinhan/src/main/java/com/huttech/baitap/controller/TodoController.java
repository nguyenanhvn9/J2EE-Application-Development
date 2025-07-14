package com.huttech.baitap.controller;

import com.huttech.baitap.model.Todoltem;
import com.huttech.baitap.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(required = false) String filter) {
        model.addAttribute("todoItems", todoService.getFilteredItems(filter));
        model.addAttribute("newTodo", new Todoltem(null, ""));
        model.addAttribute("activeItemCount", todoService.getActiveItemCount());
        model.addAttribute("currentFilter", filter != null ? filter : "all");
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@ModelAttribute("newTodo") Todoltem newTodo, RedirectAttributes redirectAttributes) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodoCompleted(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/";
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted() {
        todoService.clearCompletedItems();
        return "redirect:/";
    }
}