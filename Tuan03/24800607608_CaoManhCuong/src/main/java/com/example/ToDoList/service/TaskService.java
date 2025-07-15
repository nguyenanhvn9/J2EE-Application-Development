package com.example.ToDoList.service;

import com.example.ToDoList.model.Task;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    void addTask(Task task);
    void toggleCompleted(Long id);
    void deleteTask(Long id);
    List<Task> getActiveTasks();
    List<Task> getCompletedTasks();
    void deleteAllCompleted();

}
