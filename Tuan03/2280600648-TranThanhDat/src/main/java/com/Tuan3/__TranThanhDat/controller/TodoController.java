package com.Tuan3.__TranThanhDat.controller;

import com.Tuan3.__TranThanhDat.model.TodoItem;
import com.Tuan3.__TranThanhDat.service.TodoService;
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
    model.addAttribute("newTodo", new TodoItem());
    model.addAttribute("todoList", todoService.getAllItems(filter));  // OK với null
    model.addAttribute("activeCount", todoService.countActive());
    return "index";
}


    @PostMapping("/add")
    public String add(@ModelAttribute("newTodo") com.Tuan3.__TranThanhDat.model.TodoItem newTodo) {
        todoService.addItem(newTodo.getTitle());
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/todos";
    }

    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}

