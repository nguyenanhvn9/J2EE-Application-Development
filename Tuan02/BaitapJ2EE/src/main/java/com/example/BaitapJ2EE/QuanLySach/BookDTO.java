package com.example.BaitapJ2EE.QuanLySach;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;
    private String description;
    @JsonProperty("formats")
    private Formats formats;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<AuthorDTO> getAuthors() { return authors; }
    public void setAuthors(List<AuthorDTO> authors) { this.authors = authors; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Formats getFormats() { return formats; }
    public void setFormats(Formats formats) { this.formats = formats; }

    public static class AuthorDTO {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Formats {
        @JsonProperty("image/jpeg")
        private String imageJpeg;
        public String getImageJpeg() { return imageJpeg; }
        public void setImageJpeg(String imageJpeg) { this.imageJpeg = imageJpeg; }
    }
} 