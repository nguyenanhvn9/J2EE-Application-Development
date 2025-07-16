package com.example.demo.todo.repository;

import com.example.demo.todo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByCompleted(boolean completed);
}