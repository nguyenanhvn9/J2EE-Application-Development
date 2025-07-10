package phattrienungdungj2ee.example.demo.dto;

import java.util.List;

public class BookResponseDTO {
    private List<BookDTO> results;

    public List<BookDTO> getResults() {
        return results;
    }

    public void setResults(List<BookDTO> results) {
        this.results = results;
    }
}
