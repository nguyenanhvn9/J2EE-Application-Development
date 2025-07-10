package com.example.demo.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Book {
    private int id;
    private String title;
    private String author;

    // Constructors
    public Book() {}
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and Setters
    public int getId() {return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() {return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author;}
    public void setAuthor(String author) {this.author = author; }
}