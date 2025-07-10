package leducanh.name.vn.leducanh_2280600056.dto.Book;

import java.util.List;

public class GutendexBook {
    private int id;
    private String title;
    private List<GutendexAuthor> authors;
    private List<String> subjects;
    private List<String> bookshelves;
    private List<String> languages;
    private boolean copyright;
    private String mediaType;
    private GutendexFormats formats;
    private int downloadCount;

    // Constructors
    public GutendexBook() {
    }

    public GutendexBook(int id, String title, List<GutendexAuthor> authors, List<String> subjects,
            List<String> bookshelves, List<String> languages, boolean copyright,
            String mediaType, GutendexFormats formats, int downloadCount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.subjects = subjects;
        this.bookshelves = bookshelves;
        this.languages = languages;
        this.copyright = copyright;
        this.mediaType = mediaType;
        this.formats = formats;
        this.downloadCount = downloadCount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GutendexAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<GutendexAuthor> authors) {
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

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public GutendexFormats getFormats() {
        return formats;
    }

    public void setFormats(GutendexFormats formats) {
        this.formats = formats;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "GutendexBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", subjects=" + subjects +
                ", bookshelves=" + bookshelves +
                ", languages=" + languages +
                ", copyright=" + copyright +
                ", mediaType='" + mediaType + '\'' +
                ", formats=" + formats +
                ", downloadCount=" + downloadCount +
                '}';
    }
}