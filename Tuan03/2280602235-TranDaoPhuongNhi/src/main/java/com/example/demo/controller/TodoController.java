package com.example.demo.controller;

import com.example.demo.model.TodoItem;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/todos"})

public class TodoController {
    @Autowired
   private TodoService todoService;

   public TodoController() {
   }

   @GetMapping
   public String listTodos(@RequestParam(required = false) String filter, Model model) {
      model.addAttribute("todos", this.todoService.getAllItems(filter));
      model.addAttribute("newTodo", new TodoItem());
      model.addAttribute("activeCount", this.todoService.countActiveItems());
      model.addAttribute("filter", filter != null ? filter : "all");
      return "todos";
   }

   @PostMapping({"/add"})
   public String addTodo(@ModelAttribute("newTodo") TodoItem todo) {
      this.todoService.addItem(todo);
      return "redirect:/todos";
   }

   @PostMapping({"/{id}/toggle"})
   public String toggleTodo(@PathVariable Long id) {
      this.todoService.toggleCompleted(id);
      return "redirect:/todos";
   }

   @GetMapping({"/{id}/delete"})
   public String deleteTodo(@PathVariable Long id) {
      this.todoService.deleteItem(id);
      return "redirect:/todos";
   }

   @PostMapping({"/clear-completed"})
   public String clearCompleted() {
      this.todoService.clearCompleted();
      return "redirect:/todos";
   }
}
