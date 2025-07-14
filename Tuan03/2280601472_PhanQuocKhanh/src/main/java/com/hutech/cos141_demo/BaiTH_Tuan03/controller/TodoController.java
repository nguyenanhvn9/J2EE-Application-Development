package com.hutech.cos141_demo.BaiTH_Tuan03.controller;

import com.hutech.cos141_demo.BaiTH_Tuan03.model.TodoItem;
import com.hutech.cos141_demo.BaiTH_Tuan03.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService = new TodoService();

    @GetMapping("")
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter, Model model) {
        model.addAttribute("todos", todoService.getFilteredItems(filter));
        model.addAttribute("activeCount", todoService.getActiveCount());
        model.addAttribute("filter", filter);
        model.addAttribute("newTodo", new TodoItem());
        return "todo/index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TodoItem newTodo) {
        if (newTodo.getTitle() != null && !newTodo.getTitle().trim().isEmpty()) {
            todoService.addItem(newTodo.getTitle().trim());
        }
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.toggleCompleted(id);
        return filter != null ? "redirect:/todos?filter=" + filter : "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.deleteItem(id);
        return filter != null ? "redirect:/todos?filter=" + filter : "redirect:/todos";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted(@RequestParam(value = "filter", required = false) String filter) {
        todoService.clearCompleted();
        return filter != null ? "redirect:/todos?filter=" + filter : "redirect:/todos";
    }
}
// hi 