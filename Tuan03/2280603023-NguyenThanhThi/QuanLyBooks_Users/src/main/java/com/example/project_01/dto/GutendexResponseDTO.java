package com.example.project_01.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutendexResponseDTO {
    private List<GutendexBookDTO> results;
} 