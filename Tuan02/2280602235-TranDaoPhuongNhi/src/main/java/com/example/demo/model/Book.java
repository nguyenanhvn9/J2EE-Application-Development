package com.example.demo.model;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private List<Author> authors;
    // getter, setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public static class Author {
        private String name;
        // getter, setter

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}