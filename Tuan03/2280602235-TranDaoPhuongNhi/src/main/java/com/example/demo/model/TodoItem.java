package com.example.demo.model;

public class TodoItem {
   private Long id;
   private String title;
   private boolean completed;

   public TodoItem() {
      this.completed = false;
   }

   public TodoItem(Long id, String title) {
      this.id = id;
      this.title = title;
      this.completed = false;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public boolean isCompleted() {
      return this.completed;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }
}
