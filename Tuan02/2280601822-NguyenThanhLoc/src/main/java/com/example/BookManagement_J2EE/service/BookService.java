package com.example.BookManagement_J2EE.service;

import com.example.BookManagement_J2EE.DTO.BookDTO;
import com.example.BookManagement_J2EE.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.stream.Stream;
import com.example.BookManagement_J2EE.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.Cacheable;

@Service
public class BookService {
  private List<Book> books = new ArrayList<>();
  public List<Book> getAllBooks() {
    return books;
  }
  public Book getBookById(int id) {
    return books.stream().filter(book -> book.getId() == id).findFirst()
      .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
  }
  public void addBook(BookDTO dto) {
    if (dto == null || dto.getTitle() == null || dto.getTitle().trim().isEmpty() ||
        dto.getAuthors() == null || dto.getAuthors().isEmpty() ||
        dto.getAuthors().get(0).getName() == null || dto.getAuthors().get(0).getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Title and author must not be null or empty");
    }
    for (Book book : books) {
      if (book.getId() == dto.getId()) {
        throw new IllegalArgumentException("Book with this ID already exists.");
      }
    }
    String author = dto.getAuthors().get(0).getName();
    books.add(new Book(dto.getId(), dto.getTitle(), author));
  }
  public Optional<BookDTO> updateBook(int id, BookDTO dto) {
    if (dto == null || dto.getTitle() == null || dto.getTitle().trim().isEmpty() ||
        dto.getAuthors() == null || dto.getAuthors().isEmpty() ||
        dto.getAuthors().get(0).getName() == null || dto.getAuthors().get(0).getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Title and author must not be null or empty");
    }
    for (Book book : books) {
      if (book.getId() == id) {
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthors().get(0).getName());
        BookDTO updated = new BookDTO();
        updated.setId(book.getId());
        updated.setTitle(book.getTitle());
        BookDTO.AuthorDTO authorDTO = new BookDTO.AuthorDTO();
        authorDTO.setName(book.getAuthor());
        updated.setAuthors(java.util.Collections.singletonList(authorDTO));
        updated.setLanguages(java.util.Collections.singletonList("en"));
        return Optional.of(updated);
      }
    }
    return Optional.empty();
  }
  public boolean deleteBook(int id) {
    return books.removeIf(book -> book.getId() == id);
  }

  @Cacheable("booksApi")
  public void fetchBooksFromApi() {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://gutendex.com/books";
    ResponseEntity<GutendexResponse> response = restTemplate.getForEntity(url, GutendexResponse.class);
    if (response.getBody() != null && response.getBody().getResults() != null) {
      HashSet<Integer> existingIds = books.stream().map(Book::getId).collect(Collectors.toCollection(HashSet::new));
      for (BookDTO dto : response.getBody().getResults()) {
        if (!existingIds.contains(dto.getId())) {
          String author = (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) ? dto.getAuthors().get(0).getName() : "Unknown";
          books.add(new Book(dto.getId(), dto.getTitle(), author));
        }
      }
    }
  }

  public List<BookDTO> getBookDTOs() {
    List<BookDTO> dtos = new ArrayList<>();
    for (Book book : books) {
      BookDTO dto = new BookDTO();
      dto.setId(book.getId());
      dto.setTitle(book.getTitle());
      BookDTO.AuthorDTO authorDTO = new BookDTO.AuthorDTO();
      authorDTO.setName(book.getAuthor());
      dto.setAuthors(java.util.Collections.singletonList(authorDTO));
      dto.setLanguages(java.util.Collections.singletonList("en")); // mặc định tiếng Anh
      dtos.add(dto);
    }
    return dtos;
  }

  public List<BookDTO> getAllBooks(String author, Integer page, Integer size) {
    // Lazy loading
    fetchBooksFromApi();
    Stream<Book> stream = books.stream();
    if (author != null && !author.trim().isEmpty()) {
      String lower = author.toLowerCase();
      stream = stream.filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lower));
    }
    List<BookDTO> filtered = stream
      .skip(page != null && size != null ? (long) page * size : 0)
      .limit(size != null ? size : books.size())
      .map(book -> {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        BookDTO.AuthorDTO authorDTO = new BookDTO.AuthorDTO();
        authorDTO.setName(book.getAuthor());
        dto.setAuthors(java.util.Collections.singletonList(authorDTO));
        dto.setLanguages(java.util.Collections.singletonList("en"));
        return dto;
      })
      .collect(Collectors.toList());
    return filtered;
  }

  // 4. Tìm kiếm nâng cao
  public List<BookDTO> searchBooks(String keyword) {
    if (keyword == null) keyword = "";
    String lower = keyword.toLowerCase();
    List<BookDTO> result = new ArrayList<>();
    for (Book book : books) {
      if (book.getTitle().toLowerCase().contains(lower) || book.getAuthor().toLowerCase().contains(lower)) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        BookDTO.AuthorDTO authorDTO = new BookDTO.AuthorDTO();
        authorDTO.setName(book.getAuthor());
        dto.setAuthors(java.util.Collections.singletonList(authorDTO));
        dto.setLanguages(java.util.Collections.singletonList("en"));
        result.add(dto);
      }
    }
    return result;
  }

  // DTO cho response tổng thể của Gutendex
  public static class GutendexResponse {
    private java.util.List<BookDTO> results;
    public java.util.List<BookDTO> getResults() { return results; }
    public void setResults(java.util.List<BookDTO> results) { this.results = results; }
  }
}