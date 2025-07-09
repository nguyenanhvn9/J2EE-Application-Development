package com.example.demo.dto;

import java.util.List;

public class GutendexResponseDTO {
    private List<GutendexBookDTO> results;

    public List<GutendexBookDTO> getResults() {
        return results;
    }

    public void setResults(List<GutendexBookDTO> results) {
        this.results = results;
    }
} 