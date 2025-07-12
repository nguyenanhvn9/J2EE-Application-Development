package com.example.QLS.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;
    private List<String> languages;
}
