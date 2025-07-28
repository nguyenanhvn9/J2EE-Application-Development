package org.example.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "BookCruds")
public class BookCrud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;


    public BookCrud(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }


}
