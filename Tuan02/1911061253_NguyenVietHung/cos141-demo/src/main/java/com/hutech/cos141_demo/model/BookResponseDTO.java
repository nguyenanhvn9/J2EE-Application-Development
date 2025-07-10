package com.hutech.cos141_demo.model;

import java.util.List;

public class BookResponseDTO {
    private int count;
    private String next;
    private String previous;
    private List<BookItemDTO> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<BookItemDTO> getResults() {
        return results;
    }

    public void setResults(List<BookItemDTO> results) {
        this.results = results;
    }
}