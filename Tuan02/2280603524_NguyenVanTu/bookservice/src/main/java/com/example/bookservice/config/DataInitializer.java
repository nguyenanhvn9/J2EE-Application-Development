package com.example.bookservice.config;

import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Chỉ thêm dữ liệu mẫu nếu database rỗng
        if (bookRepository.count() == 0) {
            initializeSampleBooks();
        }
    }

    private void initializeSampleBooks() {
        try {
            System.out.println("Initializing sample books data...");

            // Sách 1: Pride and Prejudice
            Book book1 = new Book();
            book1.setTitle("Pride and Prejudice");
            book1.setAuthors(Arrays.asList("Jane Austen"));
            book1.setLanguages(Arrays.asList("en"));
            book1.setSubjects(Arrays.asList("England -- Social life and customs -- 19th century -- Fiction", "Love stories", "Young women -- Fiction"));
            book1.setDownloadCount(50000);
            book1.setGutenbergId(1342);

            // Sách 2: Alice's Adventures in Wonderland
            Book book2 = new Book();
            book2.setTitle("Alice's Adventures in Wonderland");
            book2.setAuthors(Arrays.asList("Lewis Carroll"));
            book2.setLanguages(Arrays.asList("en"));
            book2.setSubjects(Arrays.asList("Fantasy fiction", "Children's stories", "Alice (Fictitious character from Carroll) -- Fiction"));
            book2.setDownloadCount(45000);
            book2.setGutenbergId(11);

            // Sách 3: Frankenstein
            Book book3 = new Book();
            book3.setTitle("Frankenstein; Or, The Modern Prometheus");
            book3.setAuthors(Arrays.asList("Mary Wollstonecraft Shelley"));
            book3.setLanguages(Arrays.asList("en"));
            book3.setSubjects(Arrays.asList("Science fiction", "Gothic fiction", "Frankenstein's monster (Fictitious character) -- Fiction"));
            book3.setDownloadCount(40000);
            book3.setGutenbergId(84);

            // Sách 4: The Adventures of Sherlock Holmes
            Book book4 = new Book();
            book4.setTitle("The Adventures of Sherlock Holmes");
            book4.setAuthors(Arrays.asList("Arthur Conan Doyle"));
            book4.setLanguages(Arrays.asList("en"));
            book4.setSubjects(Arrays.asList("Detective and mystery stories", "Holmes, Sherlock (Fictitious character) -- Fiction", "Private investigators -- England -- Fiction"));
            book4.setDownloadCount(35000);
            book4.setGutenbergId(1661);

            // Sách 5: The Great Gatsby
            Book book5 = new Book();
            book5.setTitle("The Great Gatsby");
            book5.setAuthors(Arrays.asList("F. Scott Fitzgerald"));
            book5.setLanguages(Arrays.asList("en"));
            book5.setSubjects(Arrays.asList("Psychological fiction", "Rich people -- Fiction", "Long Island (N.Y.) -- Fiction"));
            book5.setDownloadCount(42000);
            book5.setGutenbergId(64317);

            // Lưu tất cả vào database
            bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5));
            
            System.out.println("Sample books data initialized successfully! Added " + bookRepository.count() + " books.");
            
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
