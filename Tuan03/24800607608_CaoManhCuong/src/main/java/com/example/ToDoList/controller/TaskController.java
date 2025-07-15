package com.example.ToDoList.controller;

import com.example.ToDoList.model.Task;
import com.example.ToDoList.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.addTask(task);
        return "redirect:/";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        taskService.toggleCompleted(id);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
    @GetMapping("/")
    public String viewTasks(@RequestParam(value = "filter", required = false, defaultValue = "all") String filter, Model model) {
        List<Task> tasks;
        switch (filter) {
            case "active":
                tasks = taskService.getActiveTasks();
                break;
            case "completed":
                tasks = taskService.getCompletedTasks();
                break;
            default:
                tasks = taskService.getAllTasks();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/clear-completed")
    public String clearCompletedTasks() {
        taskService.deleteAllCompleted();
        return "redirect:/";
    }

}
