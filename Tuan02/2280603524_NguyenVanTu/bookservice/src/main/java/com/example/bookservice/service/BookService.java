package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.dto.GutendexResponseDTO;
import com.example.bookservice.dto.GutendexBookDTO;
import com.example.bookservice.dto.AuthorDTO;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gutendx.api.url:https://gutendex.com/books}")
    private String gutendxApiUrl;

    // Lấy tất cả sách với lazy loading
    public List<BookDTO> getAllBooks() {
        System.out.println("=== getAllBooks() called ===");
        long count = bookRepository.count();
        System.out.println("Current book count in database: " + count);
        
        // Lazy loading: nếu không có sách nào, fetch từ API
        if (count == 0) {
            System.out.println("No books found, fetching from API...");
            fetchBooksFromGutendxApi();
            count = bookRepository.count();
            System.out.println("After fetching, book count: " + count);
        }
        
        List<Book> books = bookRepository.findAll();
        System.out.println("Retrieved " + books.size() + " books from database");
        
        List<BookDTO> result = books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        System.out.println("Returning " + result.size() + " books as DTOs");
        return result;
    }

    // Lấy sách với phân trang và lọc
    public List<BookDTO> getAllBooks(String author, int page, int size) {
        List<Book> books;
        
        if (bookRepository.count() == 0) {
            fetchBooksFromGutendxApi();
        }
        
        if (author != null && !author.trim().isEmpty()) {
            books = bookRepository.findByAuthorContainingIgnoreCase(author);
        } else {
            books = bookRepository.findAll();
        }
        
        // Áp dụng phân trang thủ công
        return books.stream()
                .skip((long) page * size)
                .limit(size)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Fetch dữ liệu từ Gutendx API
    private void fetchBooksFromGutendxApi() {
        try {
            System.out.println("=== Starting fetchBooksFromGutendxApi ===");
            String url = gutendxApiUrl + "?page=1";
            System.out.println("Calling API URL: " + url);
            
            GutendexResponseDTO response = restTemplate.getForObject(url, GutendexResponseDTO.class);
            System.out.println("API Response received: " + (response != null ? "Yes" : "No"));
            
            if (response != null) {
                System.out.println("Response count: " + response.getCount());
                System.out.println("Response results: " + (response.getResults() != null ? response.getResults().size() : "null"));
                
                if (response.getResults() != null && !response.getResults().isEmpty()) {
                    System.out.println("Found " + response.getResults().size() + " books from API");
                    
                    List<Book> books = response.getResults().stream()
                            .map(this::convertGutendexToBook)
                            .collect(Collectors.toList());
                    
                    System.out.println("Converted " + books.size() + " books, saving to database...");
                    List<Book> savedBooks = bookRepository.saveAll(books);
                    System.out.println("Successfully saved " + savedBooks.size() + " books to database");
                } else {
                    System.out.println("No books in API response results");
                }
            } else {
                System.out.println("API response is null");
            }
        } catch (Exception e) {
            System.err.println("Error fetching books from Gutendx API: " + e.getMessage());
            e.printStackTrace();
            
            // Thêm một vài sách mẫu nếu API fail
            System.out.println("Adding sample books as fallback...");
            addSampleBooks();
        }
    }
    
    // Thêm sách mẫu khi API fail
    private void addSampleBooks() {
        try {
            Book book1 = new Book();
            book1.setTitle("Pride and Prejudice");
            book1.setAuthors(List.of("Jane Austen"));
            book1.setSubjects(List.of("Fiction", "Romance"));
            book1.setLanguages(List.of("en"));
            book1.setDownloadCount(1000);
            book1.setCopyright("false");
            book1.setGutenbergId(1342);
            
            Book book2 = new Book();
            book2.setTitle("Alice's Adventures in Wonderland");
            book2.setAuthors(List.of("Lewis Carroll"));
            book2.setSubjects(List.of("Fiction", "Fantasy"));
            book2.setLanguages(List.of("en"));
            book2.setDownloadCount(2000);
            book2.setCopyright("false");
            book2.setGutenbergId(11);
            
            bookRepository.saveAll(List.of(book1, book2));
            System.out.println("Added 2 sample books successfully");
        } catch (Exception e) {
            System.err.println("Error adding sample books: " + e.getMessage());
        }
    }

    // Chuyển đổi GutendexBookDTO sang Book
    private Book convertGutendexToBook(GutendexBookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle() != null ? dto.getTitle() : "Unknown");
        book.setGutenbergId(dto.getId());
        book.setDownloadCount(dto.getDownloadCount());
        book.setCopyright(dto.getCopyright());
        book.setSubjects(dto.getSubjects());
        book.setLanguages(dto.getLanguages());
        
        // Chuyển đổi authors
        if (dto.getAuthors() != null) {
            List<String> authorNames = dto.getAuthors().stream()
                    .map(AuthorDTO::getName)
                    .filter(name -> name != null && !name.trim().isEmpty())
                    .collect(Collectors.toList());
            book.setAuthors(authorNames.isEmpty() ? List.of("Unknown") : authorNames);
        } else {
            book.setAuthors(List.of("Unknown"));
        }
        
        return book;
    }

    // Lấy sách theo ID
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return convertToDTO(book);
    }

    // Tạo sách mới với validation
    @CacheEvict(value = "books", allEntries = true)
    public BookDTO createBook(BookDTO bookDTO) {
        validateBook(bookDTO);
        
        // Kiểm tra trùng lặp Gutenberg ID
        if (bookDTO.getGutenbergId() != null) {
            Book existingBook = bookRepository.findByGutenbergId(bookDTO.getGutenbergId());
            if (existingBook != null) {
                throw new IllegalArgumentException("Book with Gutenberg ID " + bookDTO.getGutenbergId() + " already exists.");
            }
        }
        
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    // Cập nhật sách với validation
    public Optional<BookDTO> updateBook(Long id, BookDTO bookDTO) {
        validateBook(bookDTO);
        
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDTO.getTitle());
                    existingBook.setAuthors(bookDTO.getAuthors());
                    existingBook.setSubjects(bookDTO.getSubjects());
                    existingBook.setLanguages(bookDTO.getLanguages());
                    existingBook.setDownloadCount(bookDTO.getDownloadCount());
                    existingBook.setCopyright(bookDTO.getCopyright());
                    existingBook.setGutenbergId(bookDTO.getGutenbergId());
                    
                    Book updatedBook = bookRepository.save(existingBook);
                    return convertToDTO(updatedBook);
                });
    }

    // Xóa sách
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Tìm kiếm sách nâng cao
    public List<BookDTO> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        
        // Tìm kiếm theo title hoặc author
        List<Book> booksByTitle = bookRepository.findByTitleContainingIgnoreCase(keyword);
        List<Book> booksByAuthor = bookRepository.findByAuthorContainingIgnoreCase(keyword);
        
        // Kết hợp kết quả và loại bỏ trùng lặp
        return Stream.concat(booksByTitle.stream(), booksByAuthor.stream())
                .distinct()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm sách theo title
    public List<BookDTO> searchByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm sách theo author
    public List<BookDTO> searchByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Đếm số lượng sách
    public long countBooks() {
        return bookRepository.count();
    }

    // Validation cho Book
    private void validateBook(BookDTO bookDTO) {
        if (bookDTO.getTitle() == null || bookDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        
        if (bookDTO.getAuthors() == null || bookDTO.getAuthors().isEmpty() || 
            bookDTO.getAuthors().stream().allMatch(author -> author == null || author.trim().isEmpty())) {
            throw new IllegalArgumentException("At least one author is required");
        }
    }

    // Chuyển đổi Entity sang DTO
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthors(book.getAuthors());
        dto.setSubjects(book.getSubjects());
        dto.setLanguages(book.getLanguages());
        dto.setDownloadCount(book.getDownloadCount());
        dto.setCopyright(book.getCopyright());
        dto.setGutenbergId(book.getGutenbergId());
        return dto;
    }

    // Chuyển đổi DTO sang Entity
    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthors(dto.getAuthors());
        book.setSubjects(dto.getSubjects());
        book.setLanguages(dto.getLanguages());
        book.setDownloadCount(dto.getDownloadCount());
        book.setCopyright(dto.getCopyright());
        book.setGutenbergId(dto.getGutenbergId());
        return book;
    }
}