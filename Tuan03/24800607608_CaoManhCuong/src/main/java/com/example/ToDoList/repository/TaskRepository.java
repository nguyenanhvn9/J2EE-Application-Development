package com.example.ToDoList.repository;

import com.example.ToDoList.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletedTrue();
    List<Task> findByCompletedFalse();

}
