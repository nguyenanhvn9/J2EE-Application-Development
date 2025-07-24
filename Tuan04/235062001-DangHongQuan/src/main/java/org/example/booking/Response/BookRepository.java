package org.example.booking.Response;

import lombok.Data;
import org.example.booking.dto.BookDTO;
import org.example.booking.model.BookCrud;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Data
//public class BookResponse {
//    private List<BookDTO> results;
//
//    @Repository
//    public interface BookCrudRepository extends CrudRepository<BookCrud, Long> {
//    }
//}
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookCrud, Integer> {
}