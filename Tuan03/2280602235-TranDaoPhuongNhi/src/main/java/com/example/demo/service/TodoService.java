package com.example.demo.service;

import com.example.demo.model.TodoItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
   private List<TodoItem> items = new ArrayList<TodoItem>();
   private Long nextId = 1L;

   public TodoService() {
   }

   public List<TodoItem> getAllItems(String filter) {
      if (filter != null && !filter.equals("all")) {
         if (filter.equals("active")) {
            return (List)this.items.stream().filter((item) -> {
               return !item.isCompleted();
            }).collect(Collectors.toList());
         } else {
            return filter.equals("completed") ? (List)this.items.stream().filter(TodoItem::isCompleted).collect(Collectors.toList()) : this.items;
         }
      } else {
         return this.items;
      }
   }

   public List<TodoItem> getAllItems() {
      return this.getAllItems("all");
   }

   public int countActiveItems() {
      return (int)this.items.stream().filter((item) -> {
         return !item.isCompleted();
      }).count();
   }

   public void clearCompleted() {
      this.items.removeIf(TodoItem::isCompleted);
   }

   public TodoItem addItem(TodoItem item) {
      Long var10003 = this.nextId;
      this.nextId = var10003 + 1L;
      item.setId(var10003);
      this.items.add(item);
      return item;
   }

   public boolean toggleCompleted(Long id) {
      Optional<TodoItem> item = this.items.stream().filter((i) -> {
         return i.getId().equals(id);
      }).findFirst();
      if (item.isPresent()) {
         ((TodoItem)item.get()).setCompleted(!((TodoItem)item.get()).isCompleted());
         return true;
      } else {
         return false;
      }
   }

   public boolean deleteItem(Long id) {
      return this.items.removeIf((item) -> {
         return item.getId().equals(id);
      });
   }
}
