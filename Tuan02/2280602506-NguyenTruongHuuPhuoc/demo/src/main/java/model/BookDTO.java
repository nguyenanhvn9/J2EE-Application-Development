package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;
    
    public int getId() { return id; }
    public String getTitle() { return title; }
    public List<AuthorDTO> getAuthors() { return authors; }
}
