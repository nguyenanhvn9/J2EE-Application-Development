package com.hutech.cos141_demo.dto;

import java.util.List;

public class GutendexResponse {
    private List<BookDto> results;

    public List<BookDto> getResults() { return results; }
    public void setResults(List<BookDto> results) { this.results = results; }
}
