package com.example.ToDoList.service;

import com.example.ToDoList.model.Task;
import com.example.ToDoList.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void toggleCompleted(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    @Override
    public List<Task> getActiveTasks() {
        return taskRepository.findByCompletedFalse();
    }

    @Override
    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompletedTrue();
    }

    @Override
    public void deleteAllCompleted() {
        taskRepository.deleteAll(getCompletedTasks());
    }

}

