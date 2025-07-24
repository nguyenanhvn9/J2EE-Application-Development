package org.example.booking.controller;

import org.example.booking.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoCrudController {

    private final TodoService todoService;

    public TodoCrudController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String index(@RequestParam(defaultValue = "all") String filter, Model model) {
        model.addAttribute("todos", todoService.getFiltered(filter));
        model.addAttribute("filter", filter);
        model.addAttribute("remaining", todoService.getFiltered("active").size());
        return "todo";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title) {
        todoService.add(title);
        return "redirect:/todos";
    }

    @PostMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id,
                         @RequestParam(defaultValue = "all") String filter) {
        todoService.toggle(id);
        return "redirect:/todos?filter=" + filter;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam(defaultValue = "all") String filter) {
        todoService.delete(id);
        return "redirect:/todos?filter=" + filter;
    }

    @PostMapping("/toggle-all")
    public String toggleAll(@RequestParam boolean completed,
                            @RequestParam(defaultValue = "all") String filter) {
        todoService.toggleAll(completed);
        return "redirect:/todos?filter=" + filter;
    }

    @PostMapping("/clear-completed")
    public String clearCompleted(@RequestParam(defaultValue = "all") String filter) {
        todoService.clearCompleted();
        return "redirect:/todos?filter=" + filter;
    }
}
