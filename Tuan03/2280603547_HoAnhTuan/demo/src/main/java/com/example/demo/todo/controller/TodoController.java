package com.example.demo.todo.controller;

import com.example.demo.todo.model.TodoItem;
import com.example.demo.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping({"/", "/index"})
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String listTodos(@RequestParam(required = false) String filter, Model model) {
        logger.info("Handling GET request for / or /index with filter: {}", filter);
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("todos", todoService.getAllItems(filter));
        model.addAttribute("activeItemCount", todoService.getActiveItemCount());
        model.addAttribute("filter", filter != null ? filter : "all");
        return "index";
    }

    @PostMapping("/todos/add")
    public String addTodo(@Valid @ModelAttribute("newTodo") TodoItem todoItem, BindingResult result, Model model) {
        logger.info("Handling POST request for /todos/add");
        if (result.hasErrors()) {
            logger.warn("Validation errors for newTodo: {}", result.getAllErrors());
            model.addAttribute("todos", todoService.getAllItems(null));
            model.addAttribute("activeItemCount", todoService.getActiveItemCount());
            model.addAttribute("filter", "all");
            return "index";
        }
        todoService.addItem(todoItem);
        return "redirect:/?filter=all";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        logger.info("Handling POST request for /todos/{}/toggle", id);
        todoService.toggleCompleted(id);
        return "redirect:/?filter=" + (filter != null ? filter : "all");
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id, @RequestParam(required = false) String filter) {
        logger.info("Handling POST request for /todos/{}/delete", id);
        todoService.deleteItem(id);
        return "redirect:/?filter=" + (filter != null ? filter : "all");
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted(@RequestParam(required = false) String filter) {
        logger.info("Handling POST request for /todos/clear-completed");
        todoService.clearCompleted();
        return "redirect:/?filter=" + (filter != null ? filter : "all");
    }
}