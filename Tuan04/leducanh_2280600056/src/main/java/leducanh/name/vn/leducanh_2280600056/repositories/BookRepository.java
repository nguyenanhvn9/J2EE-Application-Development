package leducanh.name.vn.leducanh_2280600056.repositories;

import leducanh.name.vn.leducanh_2280600056.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    // BookRepository.java
    List<Books> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleKeyword, String authorKeyword);
    // BookRepository.java
    Page<Books> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    boolean existsByTitleAndAuthor(String title, String author);

}
