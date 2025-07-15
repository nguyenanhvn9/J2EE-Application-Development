package com.example.BookService.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private int id;
    private String title;
    private String author;
}
