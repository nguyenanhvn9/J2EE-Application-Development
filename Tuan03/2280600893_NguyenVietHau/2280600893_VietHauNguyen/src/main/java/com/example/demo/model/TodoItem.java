package com.example.demo.model;

public class TodoItem {
    private Long id;
    private String title;
    private boolean completed = false;

    // Constructor không tham số - BẮT BUỘC PHẢI CÓ để tránh lỗi
    public TodoItem() {
    }

    // Constructor đầy đủ nếu bạn cần
    public TodoItem(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
