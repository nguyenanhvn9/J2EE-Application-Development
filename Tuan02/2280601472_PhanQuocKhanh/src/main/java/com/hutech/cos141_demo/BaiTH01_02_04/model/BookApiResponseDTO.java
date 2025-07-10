package com.hutech.cos141_demo.BaiTH01_02_04.model;

import java.util.List;

public class BookApiResponseDTO {
    private int count;
    private String next;
    private String previous;
    private List<BookDTO> results;

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }
    public List<BookDTO> getResults() { return results; }
    public void setResults(List<BookDTO> results) { this.results = results; }
} 