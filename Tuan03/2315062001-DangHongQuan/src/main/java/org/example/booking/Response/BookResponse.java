package org.example.booking.Response;

import lombok.Data;
import org.example.booking.dto.BookDTO;

import java.util.List;

@Data
public class BookResponse {
    private List<BookDTO> results;
}
