package com.example.QLySachJ2EE.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutendexBookDTO {
    private int id;
    private String title;
    private List<GutendexAuthorDTO> authors;
    private List<String> subjects;
    private List<String> bookshelves;
    private List<String> languages;
    private boolean copyright;
    private String media_type;
    private List<GutendexFormatDTO> formats;
    private int download_count;
} 