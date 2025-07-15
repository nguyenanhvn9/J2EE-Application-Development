package com.example.todoapp.service;

import com.example.todoapp.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TodoService {

    private List<TodoItem> todoList = new ArrayList<>();
    private long idCounter = 1;

    public List<TodoItem> getAllItems() {
        return todoList;
    }

    public List<TodoItem> getFiltered(String filter) {
        if (filter.equals("active")) {
            return todoList.stream().filter(t -> !t.isCompleted()).toList();
        } else if (filter.equals("completed")) {
            return todoList.stream().filter(TodoItem::isCompleted).toList();
        } else {
            return todoList;
        }
    }

    public void addItem(TodoItem item) {
        item.setId(idCounter++);
        item.setCompleted(false);
        todoList.add(item);
    }

    public void toggleItem(long id) {
        for (TodoItem item : todoList) {
            if (item.getId() == id) {
                item.setCompleted(!item.isCompleted());
                break;
            }
        }
    }

    public void clearCompleted() {
        Iterator<TodoItem> iterator = todoList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isCompleted()) {
                iterator.remove();
            }
        }
    }
}
