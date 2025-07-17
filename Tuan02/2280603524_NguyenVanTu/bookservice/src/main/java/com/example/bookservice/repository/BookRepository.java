package com.example.bookservice.repository;

import com.example.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Tìm sách theo title (không phân biệt hoa thường)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Tìm sách theo author
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByAuthorContainingIgnoreCase(@Param("author") String author);
    
    // Tìm sách theo subject
    @Query("SELECT b FROM Book b JOIN b.subjects s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Book> findBySubjectContainingIgnoreCase(@Param("subject") String subject);
    
    // Tìm sách theo language
    @Query("SELECT b FROM Book b JOIN b.languages l WHERE LOWER(l) LIKE LOWER(CONCAT('%', :language, '%'))")
    List<Book> findByLanguageContainingIgnoreCase(@Param("language") String language);
    
    // Tìm sách theo gutenberg ID
    Book findByGutenbergId(Integer gutenbergId);
}
