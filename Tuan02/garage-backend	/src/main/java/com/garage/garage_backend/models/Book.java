package com.garage.garage_backend.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
}
