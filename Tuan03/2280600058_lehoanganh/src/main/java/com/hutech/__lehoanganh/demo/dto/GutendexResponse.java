package com.hutech.__lehoanganh.demo.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GutendexResponse {
    @JsonProperty("results")
    private List<BookDto> results;

    public List<BookDto> getResults() {
        return results;
    }

    public void setResults(List<BookDto> results) {
        this.results = results;
    }
} 