package com.example.BookManagement_J2EE.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter

public class Book {
  private int id;
  private String title;
  private String author;
}