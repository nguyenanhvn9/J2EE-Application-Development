package com.example.demo.dto;

import java.util.List;

public class GutendexResponseDto {
    private List<BookDto> results;

    public List<BookDto> getResults() { return results; }
    public void setResults(List<BookDto> results) { this.results = results; }
}
