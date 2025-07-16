package leducanh.name.vn.leducanh_2280600056.service;

import jakarta.annotation.PostConstruct;
import leducanh.name.vn.leducanh_2280600056.dto.Book.GutendexBook;
import leducanh.name.vn.leducanh_2280600056.dto.Book.GutendexResponse;
import leducanh.name.vn.leducanh_2280600056.exception.ResourceNotFoundException;
import leducanh.name.vn.leducanh_2280600056.model.Books;

import leducanh.name.vn.leducanh_2280600056.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class BookService {

    private final RestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @PostConstruct
    public void init() {
        fetchBooksFromGutendex();
    }
    public void fetchBooksFromGutendex() {
        try {
            String url = "https://gutendex.com/books";
            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

            if (response != null && response.getResults() != null) {
                List<Books> newBooks = response.getResults().stream()
                        .map(this::convertToBook)
                        .filter(book -> !bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) // tránh lưu trùng theo title + author
                        .collect(Collectors.toList());

                System.out.println("Số lượng sách mới: " + newBooks.size());
                bookRepository.saveAll(newBooks);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi fetch dữ liệu từ API: " + e.getMessage());
        }
    }


    private Books convertToBook(GutendexBook gutendexBook) {
        String title = gutendexBook.getTitle();
        if (title.length() > 500) {
            title = title.substring(0, 500); // cắt để vừa DB
        }


        String author = "";
        if (gutendexBook.getAuthors() != null && !gutendexBook.getAuthors().isEmpty()) {
            author = gutendexBook.getAuthors().stream()
                    .map(a -> a.getName())
                    .collect(Collectors.joining(", "));
        }

        return new Books(title, author);
    }


    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    public int getTotalPages(int pageSize) {
        long count = bookRepository.count();
        return (int) Math.ceil((double) count / pageSize);
    }


    public List<Books> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable).getContent();
    }

    public Books getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sách", "id", String.valueOf(id)));
    }


    public Books updateBookById(Long id, Books updatedBook) {
        Books existing = getBookById(id); // kiểm tra tồn tại

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());

        return bookRepository.save(existing);
    }


    public List<Books> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword không được để trống");
        }

        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }


    public boolean deleteBookById(Long id) {
        getBookById(id); // kiểm tra tồn tại
        bookRepository.deleteById(id);
        return true;
    }


    public Books addBook(Books book) {
//        if (book.getId() == null) {
//            Long newId = books.stream()
//                    .mapToLong(b -> b.getId() != null ? b.getId() : 0)
//                    .max()
//                    .orElse(0) + 1;
//            book.setId(newId);
//        }
//
//        // Kiểm tra trùng ID
//        if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
//            throw new IllegalArgumentException("Sách với ID " + book.getId() + " đã tồn tại");
//        }

//        books.add(book);
//        return book;
        return  bookRepository.save(book);
    }

    public int getBookCount() {
        return (int) bookRepository.count();
    }

    public void clearAllBooks() {
        bookRepository.deleteAll();
    }


    public List<Books> getBooks(String author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (author == null || author.trim().isEmpty()) {
            return bookRepository.findAll(pageable).getContent();
        }
        return bookRepository.findByAuthorContainingIgnoreCase(author, pageable).getContent();
    }

}
