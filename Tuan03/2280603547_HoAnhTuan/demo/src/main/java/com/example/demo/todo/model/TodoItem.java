package com.example.demo.todo.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoItem {
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private boolean completed;

    public TodoItem() {}

    public TodoItem(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }
}