package com.example.bookservice.dto;

import java.util.List;

public class BookResponseDTO {
    public int count;
    public String next;
    public String previous;
    public List<BookDTO> results;
}
