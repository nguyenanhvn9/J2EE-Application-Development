package org.example.booking.Response;

import org.example.booking.model.BookCrud;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public class BookCrudReponse {

    @Repository
    public interface BookCrudRepository extends CrudRepository<BookCrud, Long> {
    }
}
