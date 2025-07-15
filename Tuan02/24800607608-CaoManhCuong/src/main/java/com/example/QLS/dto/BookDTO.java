package com.example.QLS.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private List<AuthorDTO> authors;
    private List<String> languages;
}
