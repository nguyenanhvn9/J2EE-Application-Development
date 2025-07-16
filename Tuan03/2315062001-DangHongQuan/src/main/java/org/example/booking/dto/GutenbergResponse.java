package org.example.booking.dto;

import java.util.List;

public class GutenbergResponse {
    private List<BookDTO> results;

    public List<BookDTO> getResults() {
        return results;
    }

    public void setResults(List<BookDTO> results) {
        this.results = results;
    }
}
