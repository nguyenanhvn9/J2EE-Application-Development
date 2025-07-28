package com.example.demo.repository;

import com.example.demo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<TodoItem, UUID> {
    List<TodoItem> findByCompleted(boolean completed);
}
