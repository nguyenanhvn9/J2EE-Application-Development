package com.tuan3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoItem {
    private Long id;
    private String title;
    private boolean completed = false;
}