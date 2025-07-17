package com.example.bookservice.dto;

import java.util.List;

public class GutendexResponseDTO {
    private int count;
    private String next;
    private String previous;
    private List<GutendexBookDTO> results;

    // Getters and Setters
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }

    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }

    public List<GutendexBookDTO> getResults() { return results; }
    public void setResults(List<GutendexBookDTO> results) { this.results = results; }
}
