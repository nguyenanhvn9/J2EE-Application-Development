package com.example.BookManagement_J2EE.DTO;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;
    private List<String> languages;

    @Data
    public static class AuthorDTO {
        private String name;
    }
} 