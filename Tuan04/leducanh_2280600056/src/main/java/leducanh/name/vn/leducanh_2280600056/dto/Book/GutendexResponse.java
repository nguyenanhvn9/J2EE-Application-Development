package leducanh.name.vn.leducanh_2280600056.dto.Book;

import java.util.List;

public class GutendexResponse {
    private int count;
    private String next;
    private String previous;
    private List<GutendexBook> results;

    // Constructors
    public GutendexResponse() {
    }

    public GutendexResponse(int count, String next, String previous, List<GutendexBook> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    // Getters and Setters
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

    public List<GutendexBook> getResults() {
        return results;
    }

    public void setResults(List<GutendexBook> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GutendexResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}