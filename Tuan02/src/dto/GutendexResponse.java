package com.example.dto;

import java.util.List;

public class GutendexResponse {
    private List<GutendexBookDTO> results;

    // Default constructor
    public GutendexResponse() {
    }

    // Constructor có tham số (nếu cần)
    public GutendexResponse(List<GutendexBookDTO> results) {
        this.results = results;
    }

    // Getter
    public List<GutendexBookDTO> getResults() {
        return results;
    }

    // Setter
    public void setResults(List<GutendexBookDTO> results) {
        this.results = results;
    }
}
