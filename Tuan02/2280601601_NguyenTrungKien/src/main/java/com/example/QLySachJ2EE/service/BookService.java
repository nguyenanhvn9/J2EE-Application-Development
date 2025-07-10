package com.example.QLySachJ2EE.service;

import com.example.QLySachJ2EE.dto.GutendexBookDTO;
import com.example.QLySachJ2EE.dto.GutendexResponseDTO;
import com.example.QLySachJ2EE.exception.ResourceNotFoundException;
import com.example.QLySachJ2EE.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class  BookService {
    private List<Book> books = new ArrayList<>();
    private boolean isDataLoaded = false;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Cacheable("books")
    public List<Book> getAllBooks() {
        if (!isDataLoaded) {
            fetchBooksFromApi();
        }
        return books;
    }
    
    // Phân trang và lọc theo tác giả
    @Cacheable(value = "books", key = "#author + '-' + #page + '-' + #size")
    public List<Book> getAllBooks(String author, Integer page, Integer size) {
        if (!isDataLoaded) {
            fetchBooksFromApi();
        }
        
        List<Book> filteredBooks = books;
        
        // Lọc theo tác giả nếu có
        if (author != null && !author.trim().isEmpty()) {
            String lowerAuthor = author.toLowerCase().trim();
            filteredBooks = books.stream()
                    .filter(b -> b.getAuthor() != null && 
                            b.getAuthor().toLowerCase().contains(lowerAuthor))
                    .toList();
        }
        
        // Phân trang
        if (page != null && size != null && page >= 0 && size > 0) {
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, filteredBooks.size());
            
            if (startIndex < filteredBooks.size()) {
                return filteredBooks.subList(startIndex, endIndex);
            } else {
                return new ArrayList<>();
            }
        }
        
        return filteredBooks;
    }
    
    @Cacheable(value = "book", key = "#id")
    public Book getBookById(int id) {
        if (!isDataLoaded) {
            fetchBooksFromApi();
        }
        Book book = books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
        if (book == null) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        return book;
    }
    
    // Kiểm tra trùng lặp ID khi thêm mới
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public boolean addBook(Book book) {
        // Validation dữ liệu đầu vào
        validateBook(book);
        
        // Kiểm tra ID đã tồn tại
        if (books.stream().anyMatch(existingBook -> existingBook.getId() == book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        
        books.add(book);
        return true;
    }
    
    // Cập nhật với validation và trả về Optional
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public Optional<Book> updateBook(int id, Book updatedBook) {
        // Validation dữ liệu đầu vào
        validateBook(updatedBook);
        
        Optional<Book> existingBook = books.stream()
                .filter(b -> b.getId() == id)
                .findFirst();
        
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            return Optional.of(book);
        }
        
        return Optional.empty();
    }
    
    // Xóa với trả về boolean
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }
    
    // Tìm kiếm nâng cao
    @Cacheable(value = "bookSearch", key = "#keyword")
    public List<Book> searchBooks(String keyword) {
        if (!isDataLoaded) {
            fetchBooksFromApi();
        }
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        
        return books.stream()
                .filter(b -> 
                    (b.getTitle() != null && b.getTitle().toLowerCase().contains(lowerKeyword)) ||
                    (b.getAuthor() != null && b.getAuthor().toLowerCase().contains(lowerKeyword))
                )
                .toList();
    }
    
    // Validation dữ liệu đầu vào
    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
    }
    
    private void fetchBooksFromApi() {
        try {
            String apiUrl = "https://gutendex.com/books";
            GutendexResponseDTO response = restTemplate.getForObject(apiUrl, GutendexResponseDTO.class);
            
            if (response != null && response.getResults() != null) {
                for (GutendexBookDTO gutendexBook : response.getResults()) {
                    Book book = new Book();
                    book.setId(gutendexBook.getId());
                    book.setTitle(gutendexBook.getTitle());
                    
                    // Lấy tên tác giả đầu tiên nếu có
                    if (gutendexBook.getAuthors() != null && !gutendexBook.getAuthors().isEmpty()) {
                        book.setAuthor(gutendexBook.getAuthors().get(0).getName());
                    } else {
                        book.setAuthor("Unknown Author");
                    }
                    
                    books.add(book);
                }
            }
            isDataLoaded = true;
        } catch (Exception e) {
            System.err.println("Error fetching books from API: " + e.getMessage());
            // Nếu API không hoạt động, thêm một số dữ liệu mẫu
            addSampleBooks();
            isDataLoaded = true;
        }
    }
    
    private void addSampleBooks() {
        books.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book(2, "To Kill a Mockingbird", "Harper Lee"));
        books.add(new Book(3, "1984", "George Orwell"));
        books.add(new Book(4, "Pride and Prejudice", "Jane Austen"));
        books.add(new Book(5, "The Catcher in the Rye", "J.D. Salinger"));
    }
} 