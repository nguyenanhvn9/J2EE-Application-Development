package com.hutech.book_user_management.dto;

import java.util.List;

public class BookResponseDTO {
    private int count;
    private List<ResultDTO> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultDTO> results) {
        this.results = results;
    }
}
