package com.hutech.todolist.controller;

import com.hutech.todolist.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/works")
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping
    public String listWorks(Model model) {
        model.addAttribute("works", workService.getAllWorks());
        model.addAttribute("filter", null);
        return "works";
    }

    @PostMapping("/add")
    public String addWork(@RequestParam String name) {
        workService.addWork(name);
        return "redirect:/works";
    }

    @GetMapping("/toggle/{id}")
    public String toggleComplete(@PathVariable Long id) {
        workService.toggleComplete(id);
        return "redirect:/works";
    }

    @GetMapping("/delete/{id}")
    public String deleteWork(@PathVariable Long id) {
        workService.deleteWork(id);
        return "redirect:/works";
    }

    @GetMapping("/clear")
    public String clearCompleted() {
        workService.clearCompleted();
        return "redirect:/works";
    }

    @GetMapping("/filter/active")
    public String filterActive(Model model) {
        model.addAttribute("works", workService.getActiveWorks());
        model.addAttribute("filter", "active");
        return "works";
    }

    @GetMapping("/filter/completed")
    public String filterCompleted(Model model) {
        model.addAttribute("works", workService.getCompletedWorks());
        model.addAttribute("filter", "completed");
        return "works";
    }
}
