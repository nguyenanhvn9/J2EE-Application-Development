package phattrienungdungj2ee.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import phattrienungdungj2ee.example.demo.dto.BookDTO;
import phattrienungdungj2ee.example.demo.dto.BookResponseDTO;
import phattrienungdungj2ee.example.demo.exception.ResourceNotFoundException;
import phattrienungdungj2ee.example.demo.model.Book;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Book> getBooks(String author, int page, int size) {
        if (books.isEmpty()) {
            fetchBooksFromAPI();
        }
        return books.stream()
                .filter(book -> author == null || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        if (books.isEmpty()) {
            fetchBooksFromAPI();
        }
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sách với ID: " + id));
    }

    @Cacheable("books")
    private void fetchBooksFromAPI() {
        String apiUrl = "https://gutendex.com/books";
        BookResponseDTO response = restTemplate.getForObject(apiUrl, BookResponseDTO.class);

        if (response != null && response.getResults() != null) {
            books.clear();
            response.getResults().forEach(dto -> {
                Book book = new Book();
                book.setId(dto.getId());
                book.setTitle(dto.getTitle());

                String authorName = (dto.getAuthors() != null && !dto.getAuthors().isEmpty())
                        ? dto.getAuthors().get(0).getName()
                        : "Không rõ";

                book.setAuthor(authorName);
                books.add(book);
            });
        }
    }
} 