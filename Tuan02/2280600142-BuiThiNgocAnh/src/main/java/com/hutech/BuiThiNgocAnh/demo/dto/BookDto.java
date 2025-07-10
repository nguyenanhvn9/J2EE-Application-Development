package com.hutech.BuiThiNgocAnh.demo.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDto {
    private Integer id;
    private String title;
    private List<AuthorDto> authors;
    @JsonProperty("formats")
    private Formats formats;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<AuthorDto> getAuthors() { return authors; }
    public void setAuthors(List<AuthorDto> authors) { this.authors = authors; }
    public Formats getFormats() { return formats; }
    public void setFormats(Formats formats) { this.formats = formats; }

    public static class AuthorDto {
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