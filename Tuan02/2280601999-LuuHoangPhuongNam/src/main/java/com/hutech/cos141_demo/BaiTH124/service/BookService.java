package com.hutech.cos141_demo.BaiTH124.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hutech.cos141_demo.BaiTH124.dto.GutenbergAuthor;
import com.hutech.cos141_demo.BaiTH124.dto.GutenbergBook;
import com.hutech.cos141_demo.BaiTH124.dto.GutenbergFormats;
import com.hutech.cos141_demo.BaiTH124.dto.GutenbergResponse;
import com.hutech.cos141_demo.BaiTH124.dto.PaginatedResponse;
import com.hutech.cos141_demo.BaiTH124.exception.ApiException;
import com.hutech.cos141_demo.BaiTH124.exception.ResourceNotFoundException;
import com.hutech.cos141_demo.BaiTH124.model.Book;
import com.hutech.cos141_demo.BaiTH124.repository.BookRepository;

@Service
public class BookService {
    
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private boolean isLoaded = false;
    
    private static final String GUTENBERG_API_URL = "https://gutendex.com/books";
    
    @Autowired
    public BookService(RestTemplate restTemplate, BookRepository bookRepository) {
        this.restTemplate = restTemplate;
        this.bookRepository = bookRepository;
    }
    
    /**
     * Fetch books from Gutenberg API and convert them to Book entities
     */
    public void fetch() {
        try {
            System.out.println("Fetching books from Gutenberg API...");
            GutenbergResponse response = restTemplate.getForObject(GUTENBERG_API_URL, GutenbergResponse.class);
            
            if (response != null && response.getResults() != null) {
                List<Book> books = response.getResults().stream()
                        .filter(book -> book != null) // Filter out null books
                        .map(this::convertToBook)
                        .filter(book -> book != null) // Filter out null converted books
                        .collect(Collectors.toList());
                
                if (!books.isEmpty()) {
                    // Save books to database
                    bookRepository.saveAll(books);
                    isLoaded = true;
                    System.out.println("Successfully fetched and saved " + books.size() + " books from API");
                } else {
                    System.out.println("No valid books found in API response");
                }
            } else {
                throw new ApiException("Invalid response from Gutenberg API");
            }
        } catch (Exception e) {
            if (e instanceof ApiException) {
                throw e;
            }
            throw new ApiException("Error fetching books from API: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get all books with pagination and filtering - lazy loading implementation
     */
    @Cacheable("books")
    public PaginatedResponse<Book> getAllBooks(Integer page, Integer size, String author) {
        if (!isLoaded) {
            fetch();
        }
        
        // Set default values
        page = page != null ? page : 0;
        size = size != null ? size : 10;
        
        // Get all books
        List<Book> allBooks = bookRepository.findAll();
        
        // Apply author filter if provided
        if (author != null && !author.trim().isEmpty()) {
            String authorFilter = author.toLowerCase().trim();
            allBooks = allBooks.stream()
                    .filter(book -> book.getAuthor() != null && 
                            book.getAuthor().toLowerCase().contains(authorFilter))
                    .collect(Collectors.toList());
        }
        
        // Calculate pagination
        long totalElements = allBooks.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        // Validate page number
        if (page >= totalPages && totalPages > 0) {
            throw new IllegalArgumentException("Page number " + page + " is out of range. Total pages: " + totalPages);
        }
        
        // Apply pagination
        List<Book> paginatedBooks = allBooks.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
        
        return new PaginatedResponse<>(paginatedBooks, page, size, totalElements);
    }
    
    /**
     * Get all books - backward compatibility
     */
    public List<Book> getAllBooks() {
        PaginatedResponse<Book> response = getAllBooks(0, Integer.MAX_VALUE, null);
        return response.getContent();
    }
    
    /**
     * Add a new book with duplicate ID check and validation
     */
    public boolean addBook(Book book) {
        // Validate input data
        validateBookData(book);
        
        // Check if book with same ID already exists
        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            throw new IllegalArgumentException("Book with this ID already exists.");
        }
        
        // Check if book with same Gutenberg ID already exists
        if (book.getGutenbergId() != null && !book.getGutenbergId().trim().isEmpty()) {
            List<Book> existingBooks = bookRepository.findAll();
            boolean gutenbergIdExists = existingBooks.stream()
                    .anyMatch(existingBook -> book.getGutenbergId().equals(existingBook.getGutenbergId()));
            if (gutenbergIdExists) {
                throw new IllegalArgumentException("Book with this Gutenberg ID already exists.");
            }
        }
        
        // Save the book
        Book savedBook = bookRepository.save(book);
        return savedBook != null;
    }
    
    /**
     * Update an existing book
     */
    public Optional<Book> updateBook(Long id, Book book) {
        // Validate input data
        validateBookData(book);
        
        // Check if book exists
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            return Optional.empty();
        }
        
        // Update the book
        Book bookToUpdate = existingBook.get();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setLanguage(book.getLanguage());
        bookToUpdate.setDownloadCount(book.getDownloadCount());
        bookToUpdate.setSubjects(book.getSubjects());
        bookToUpdate.setBookshelves(book.getBookshelves());
        bookToUpdate.setFormats(book.getFormats());
        bookToUpdate.setGutenbergId(book.getGutenbergId());
        
        Book updatedBook = bookRepository.save(bookToUpdate);
        return Optional.of(updatedBook);
    }
    
    /**
     * Delete a book by ID
     */
    public boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return false;
        }
        
        bookRepository.deleteById(id);
        return true;
    }
    
    /**
     * Get book by ID with exception handling
     */
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    
    /**
     * Get book by ID (Optional version for backward compatibility)
     */
    public Optional<Book> getBookByIdOptional(Long id) {
        return bookRepository.findById(id);
    }
    
    /**
     * Search books by keyword (case-insensitive)
     */
    @Cacheable(value = "books", key = "#keyword")
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        
        String searchKeyword = keyword.toLowerCase().trim();
        List<Book> allBooks = getAllBooks();
        
        return allBooks.stream()
                .filter(book -> {
                    String title = book.getTitle() != null ? book.getTitle().toLowerCase() : "";
                    String author = book.getAuthor() != null ? book.getAuthor().toLowerCase() : "";
                    String subjects = book.getSubjects() != null ? book.getSubjects().toLowerCase() : "";
                    
                    return title.contains(searchKeyword) || 
                           author.contains(searchKeyword) || 
                           subjects.contains(searchKeyword);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Validate book data
     */
    private void validateBookData(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
    }
    
    /**
     * Convert GutenbergBook DTO to Book entity
     */
    private Book convertToBook(GutenbergBook gutenbergBook) {
        if (gutenbergBook == null) {
            return null;
        }
        
        Book book = new Book();
        book.setGutenbergId(String.valueOf(gutenbergBook.getId()));
        book.setTitle(gutenbergBook.getTitle() != null ? gutenbergBook.getTitle() : "Unknown Title");
        
        // Handle authors
        if (gutenbergBook.getAuthors() != null && !gutenbergBook.getAuthors().isEmpty()) {
            GutenbergAuthor author = gutenbergBook.getAuthors().get(0);
            book.setAuthor(author != null && author.getName() != null ? author.getName() : "Unknown Author");
        } else {
            book.setAuthor("Unknown Author");
        }
        
        // Handle languages
        if (gutenbergBook.getLanguages() != null && !gutenbergBook.getLanguages().isEmpty()) {
            book.setLanguage(String.join(", ", gutenbergBook.getLanguages()));
        } else {
            book.setLanguage("Unknown");
        }
        
        book.setDownloadCount(String.valueOf(gutenbergBook.getDownload_count()));
        
        // Handle subjects
        if (gutenbergBook.getSubjects() != null && !gutenbergBook.getSubjects().isEmpty()) {
            String subjects = String.join(", ", gutenbergBook.getSubjects());
            if (subjects.length() > 1000) {
                subjects = subjects.substring(0, 997) + "...";
            }
            book.setSubjects(subjects);
        } else {
            book.setSubjects("No subjects");
        }
        
        // Handle bookshelves
        if (gutenbergBook.getBookshelves() != null && !gutenbergBook.getBookshelves().isEmpty()) {
            String bookshelves = String.join(", ", gutenbergBook.getBookshelves());
            if (bookshelves.length() > 500) {
                bookshelves = bookshelves.substring(0, 497) + "...";
            }
            book.setBookshelves(bookshelves);
        } else {
            book.setBookshelves("No bookshelves");
        }
        
        // Handle formats - extract only important format URLs
        if (gutenbergBook.getFormats() != null) {
            StringBuilder formatsBuilder = new StringBuilder();
            GutenbergFormats formats = gutenbergBook.getFormats();
            
            if (formats.getText_plain() != null) {
                formatsBuilder.append("text_plain: ").append(formats.getText_plain()).append("; ");
            }
            if (formats.getText_html() != null) {
                formatsBuilder.append("text_html: ").append(formats.getText_html()).append("; ");
            }
            if (formats.getApplication_epub_zip() != null) {
                formatsBuilder.append("epub: ").append(formats.getApplication_epub_zip()).append("; ");
            }
            if (formats.getApplication_pdf() != null) {
                formatsBuilder.append("pdf: ").append(formats.getApplication_pdf()).append("; ");
            }
            
            String formatsString = formatsBuilder.toString();
            if (formatsString.isEmpty()) {
                formatsString = "No formats available";
            } else if (formatsString.length() > 1000) {
                formatsString = formatsString.substring(0, 997) + "...";
            }
            book.setFormats(formatsString);
        } else {
            book.setFormats("No formats available");
        }
        
        return book;
    }
    
    /**
     * Clear loaded books and reset loading state
     */
    public void clearBooks() {
        bookRepository.deleteAll();
        isLoaded = false;
    }
    
    /**
     * Check if books are loaded
     */
    public boolean isLoaded() {
        return isLoaded;
    }
} 