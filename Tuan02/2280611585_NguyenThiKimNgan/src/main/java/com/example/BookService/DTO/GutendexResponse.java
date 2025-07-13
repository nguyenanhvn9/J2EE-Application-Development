package com.example.BookService.DTO;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GutendexResponse {
    private List<BookDTO> results;
}