package com.example.j2ee.service;

import com.example.j2ee.dto.BookDTO;
import com.example.j2ee.dto.BooksResponseDTO;
import com.example.j2ee.exception.ResourceNotFoundException;
import com.example.j2ee.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    
    private final List<Book> books = new ArrayList<>();
    private final RestTemplate restTemplate;
    
    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Cacheable("books")
    public List<Book> getAllBooks() {
        if (books.isEmpty()) {
            fetchBooksFromApi();
        }
        return new ArrayList<>(books);
    }
    
    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        List<Book> allBooks = getAllBooks();
        
        if (author != null && !author.trim().isEmpty()) {
            allBooks = allBooks.stream()
                    .filter(book -> book.getAuthor() != null && 
                            book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (page != null && size != null && page >= 0 && size > 0) {
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, allBooks.size());
            
            if (startIndex < allBooks.size()) {
                return allBooks.subList(startIndex, endIndex);
            } else {
                return new ArrayList<>();
            }
        }
        
        return allBooks;
    }
    
    public Book getBookById(Integer id) {
        List<Book> allBooks = getAllBooks();
        return allBooks.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    
    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
        
        if (books.stream().anyMatch(existingBook -> existingBook.getId().equals(book.getId()))) {
            throw new IllegalArgumentException("Book with this ID already exists");
        }
        
        books.add(book);
        return true;
    }
    
    public Optional<Book> updateBook(Integer id, Book updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
        
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                return Optional.of(updatedBook);
            }
        }
        return Optional.empty();
    }
    
    public boolean deleteBook(Integer id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
    
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        
        String lowerKeyword = keyword.toLowerCase();
        return getAllBooks().stream()
                .filter(book -> (book.getTitle() != null && 
                                book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                               (book.getAuthor() != null && 
                                book.getAuthor().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }
    
    private void fetchBooksFromApi() {
        try {
            String apiUrl = "https://gutendex.com/books";
            BooksResponseDTO response = restTemplate.getForObject(apiUrl, BooksResponseDTO.class);
            
            if (response != null && response.getResults() != null) {
                for (BookDTO bookDTO : response.getResults()) {
                    Book book = convertDTOToBook(bookDTO);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching books from API: " + e.getMessage());
            addDefaultBooks();
        }
    }
    
    private Book convertDTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        
        if (bookDTO.getAuthors() != null && !bookDTO.getAuthors().isEmpty()) {
            book.setAuthor(bookDTO.getAuthors().get(0).getName());
        } else {
            book.setAuthor("Unknown Author");
        }
        
        if (bookDTO.getLanguages() != null && !bookDTO.getLanguages().isEmpty()) {
            book.setLanguages(String.join(", ", bookDTO.getLanguages()));
        }
        
        if (bookDTO.getSubjects() != null && !bookDTO.getSubjects().isEmpty()) {
            book.setSubjects(String.join(", ", bookDTO.getSubjects()));
        }
        
        return book;
    }
    
    private void addDefaultBooks() {
        books.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book(2, "To Kill a Mockingbird", "Harper Lee"));
        books.add(new Book(3, "1984", "George Orwell"));
        books.add(new Book(4, "Pride and Prejudice", "Jane Austen"));
        books.add(new Book(5, "The Catcher in the Rye", "J.D. Salinger"));
    }
} 