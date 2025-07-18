package com.example.QLS.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutendexResponse {
    private int count;
    private String next;
    private List<BookDTO> results;
}
