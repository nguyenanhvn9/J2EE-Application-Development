package com.example.project_01.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutendexBookDTO {
    private int id;
    private String title;
    private List<GutendexAuthorDTO> authors;
} 