package com.example.bookmanagement.dto;

import java.util.List;

public class GutendexResponseDto {
    private int count;
    private String next;
    private String previous;
    private List<GutendexBookDto> results;

    // Getters and Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<GutendexBookDto> getResults() {
        return results;
    }

    public void setResults(List<GutendexBookDto> results) {
        this.results = results;
    }
}
