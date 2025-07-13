package com.example.j2ee.dto;

import java.util.List;

public class BooksResponseDTO {
    private Integer count;
    private String next;
    private String previous;
    private List<BookDTO> results;
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
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
    
    public List<BookDTO> getResults() {
        return results;
    }
    
    public void setResults(List<BookDTO> results) {
        this.results = results;
    }
} 