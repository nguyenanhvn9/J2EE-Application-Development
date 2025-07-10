package com.example.QLySachJ2EE.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutendexResponseDTO {
    private int count;
    private String next;
    private String previous;
    private List<GutendexBookDTO> results;
} 