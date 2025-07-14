package leducanh.name.vn.leducanh_2280600056.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String id;
    private String title;
    private List<String> authors;
    private List<String> subjects;
    private List<String> bookshelves;
    private List<String> languages;
    private String downloadCount;
    private String mediaType;
    private String downloadUrl;
    private String authorsStr;
    private String subjectsStr;
    private String imageUrl;
    private String bookshelvesStr;
    private String languagesStr;

    // Constructors
    public Book() {
        this.authors = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.bookshelves = new ArrayList<>();
        this.languages = new ArrayList<>();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setAuthorsStr(String authorsStr) {
        this.authorsStr = authorsStr;
    }

    public void setSubjectsStr(String subjectsStr) {
        this.subjectsStr = subjectsStr;
    }

    public void setBookshelvesStr(String bookshelvesStr) {
        this.bookshelvesStr = bookshelvesStr;
    }

    public void setLanguagesStr(String languagesStr) {
        this.languagesStr = languagesStr;
    }

    public String getAuthorsStr() {
        return authorsStr;
    }

    public String getSubjectsStr() {
        return subjectsStr;
    }

    public String getBookshelvesStr() {
        return bookshelvesStr;
    }

    public String getLanguagesStr() {
        return languagesStr;
    }

    // getters
    public Book(String id, String title, List<String> authors, List<String> subjects,
            List<String> bookshelves, List<String> languages, String downloadCount,
            String mediaType, String downloadUrl) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.subjects = subjects;
        this.bookshelves = bookshelves;
        this.languages = languages;
        this.downloadCount = downloadCount;
        this.mediaType = mediaType;
        this.downloadUrl = downloadUrl;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(List<String> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", subjects=" + subjects +
                ", bookshelves=" + bookshelves +
                ", languages=" + languages +
                ", downloadCount='" + downloadCount + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}