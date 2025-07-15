package com.example.ToDoList.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoItem {
    private Long id;
    private String title;
    private boolean completed = false;

    public TodoItem(Long id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }
}

