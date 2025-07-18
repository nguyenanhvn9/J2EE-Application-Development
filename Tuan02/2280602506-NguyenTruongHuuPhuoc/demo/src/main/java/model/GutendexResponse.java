package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutendexResponse {
    private int count;
    private String next;
    private List<BookDTO> results;
    
    public List<BookDTO> getResults() {
    return results;
}
}
