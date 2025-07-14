package com.lehoang.demo_lehoang.model;

public class TodoItem {
    private Long id;
    private String title;
    private boolean completed = false;

    public TodoItem() {}

    public TodoItem(Long id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}