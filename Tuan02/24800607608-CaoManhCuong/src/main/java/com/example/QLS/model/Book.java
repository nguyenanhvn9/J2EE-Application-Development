package com.example.QLS.model;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Book {
    private Long id;
    private String title;
    @Getter
    private String author;

}
