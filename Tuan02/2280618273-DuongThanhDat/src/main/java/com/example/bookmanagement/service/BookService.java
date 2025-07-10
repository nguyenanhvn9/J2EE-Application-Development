package com.example.bookmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final Map<Long, Book> bookStorage = new ConcurrentHashMap<>();
    private static final String GUTENDEX_API_URL = "https://gutendex.com/books";
    private boolean isDataLoaded = false;

    public BookService() {
        this.restTemplate = new RestTemplate();
    }

    // Lazy Loading: Load data only when first accessed
    private void loadBooksIfNeeded() {
        if (!isDataLoaded) {
            synchronized (this) {
                if (!isDataLoaded) {
                    loadBooksFromAPI();
                    isDataLoaded = true;
                }
            }
        }
    }

    // Load books from Gutendex API
    private void loadBooksFromAPI() {
        try {
            GutendexResponseDto response = restTemplate.getForObject(GUTENDEX_API_URL, GutendexResponseDto.class);
            if (response != null && response.getResults() != null) {
                for (GutendexBookDto gutendexBook : response.getResults()) {
                    Book book = convertToBook(gutendexBook);
                    bookStorage.put(book.getId(), book);
                }
                System.out.println("Loaded " + bookStorage.size() + " books from Gutendex API");
            }
        } catch (Exception e) {
            System.err.println("Error loading books from API: " + e.getMessage());
        }
    }

    // Convert GutendexBookDto to Book
    private Book convertToBook(GutendexBookDto gutendexBook) {
        Book book = new Book();
        book.setId(gutendexBook.getId());
        book.setTitle(gutendexBook.getTitle());

        // Convert authors list to comma-separated string
        if (gutendexBook.getAuthors() != null && !gutendexBook.getAuthors().isEmpty()) {
            String authors = gutendexBook.getAuthors().stream()
                    .map(author -> author.getName())
                    .collect(Collectors.joining(", "));
            book.setAuthor(authors);
        }

        // Convert subjects list to comma-separated string
        if (gutendexBook.getSubjects() != null && !gutendexBook.getSubjects().isEmpty()) {
            String subjects = String.join(", ", gutendexBook.getSubjects());
            book.setSubjects(subjects);
        }

        book.setLanguage(gutendexBook.getLanguages() != null && !gutendexBook.getLanguages().isEmpty()
                ? gutendexBook.getLanguages().get(0) : "Unknown");
        book.setDownloadCount(gutendexBook.getDownloadCount());

        return book;
    }

    // Get all books with caching
    @Cacheable("books")
    public List<Book> getAllBooks() {
        loadBooksIfNeeded();
        return new ArrayList<>(bookStorage.values());
    }

    // Get book by ID
    @Cacheable("book")
    public Book getBookById(Long id) {
        loadBooksIfNeeded();
        Book book = bookStorage.get(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        return book;
    }

    // Search books by title (case-insensitive)
    public List<Book> searchByTitle(String title) {
        loadBooksIfNeeded();
        return bookStorage.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search books by author (case-insensitive)
    public List<Book> searchByAuthor(String author) {
        loadBooksIfNeeded();
        return bookStorage.values().stream()
                .filter(book -> book.getAuthor() != null &&
                        book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search books by subject
    public List<Book> searchBySubject(String subject) {
        loadBooksIfNeeded();
        return bookStorage.values().stream()
                .filter(book -> book.getSubjects() != null &&
                        book.getSubjects().toLowerCase().contains(subject.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get paginated books
    public List<Book> getBooksPaginated(int page, int size) {
        loadBooksIfNeeded();
        List<Book> allBooks = new ArrayList<>(bookStorage.values());

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, allBooks.size());

        if (startIndex >= allBooks.size()) {
            return new ArrayList<>();
        }

        return allBooks.subList(startIndex, endIndex);
    }

    // Get total count of books
    public long getTotalBooksCount() {
        loadBooksIfNeeded();
        return bookStorage.size();
    }

    // Add a new book (for CRUD operations)
    public Book addBook(Book book) {
        if (book.getId() == null) {
            // Generate new ID
            long newId = bookStorage.keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;
            book.setId(newId);
        }

        // Check for duplicate
        if (isDuplicateBook(book)) {
            throw new IllegalArgumentException("Book with this title and author already exists");
        }

        bookStorage.put(book.getId(), book);
        return book;
    }

    // Update book
    public Book updateBook(Long id, Book updatedBook) {
        loadBooksIfNeeded();
        Book existingBook = bookStorage.get(id);
        if (existingBook == null) {
            throw new ResourceNotFoundException("Book", "id", id);
        }

        updatedBook.setId(id);

        // Check for duplicate (excluding current book)
        if (isDuplicateBookExcluding(updatedBook, id)) {
            throw new IllegalArgumentException("Book with this title and author already exists");
        }

        bookStorage.put(id, updatedBook);
        return updatedBook;
    }

    // Delete book
    public void deleteBook(Long id) {
        loadBooksIfNeeded();
        Book book = bookStorage.remove(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
    }

    // Check for duplicate books
    private boolean isDuplicateBook(Book book) {
        return bookStorage.values().stream()
                .anyMatch(existingBook ->
                    existingBook.getTitle().equalsIgnoreCase(book.getTitle()) &&
                    existingBook.getAuthor().equalsIgnoreCase(book.getAuthor()));
    }

    // Check for duplicate books excluding specific ID
    private boolean isDuplicateBookExcluding(Book book, Long excludeId) {
        return bookStorage.values().stream()
                .filter(existingBook -> !existingBook.getId().equals(excludeId))
                .anyMatch(existingBook ->
                    existingBook.getTitle().equalsIgnoreCase(book.getTitle()) &&
                    existingBook.getAuthor().equalsIgnoreCase(book.getAuthor()));
    }
}
