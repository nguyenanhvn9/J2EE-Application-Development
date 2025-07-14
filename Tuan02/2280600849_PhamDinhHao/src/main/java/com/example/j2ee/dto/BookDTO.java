package com.example.j2ee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookDTO {
    private Integer id;
    private String title;
    private List<AuthorDTO> authors;
    private List<String> languages;
    private List<String> subjects;
    private List<FormatDTO> formats;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<AuthorDTO> getAuthors() {
        return authors;
    }
    
    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
    
    public List<String> getLanguages() {
        return languages;
    }
    
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
    
    public List<String> getSubjects() {
        return subjects;
    }
    
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
    
    public List<FormatDTO> getFormats() {
        return formats;
    }
    
    public void setFormats(List<FormatDTO> formats) {
        this.formats = formats;
    }
    
        private String name;
        private Integer birthYear;
        private Integer deathYear;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Integer getBirthYear() {
            return birthYear;
        }
        
        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }
        
        public Integer getDeathYear() {
            return deathYear;
        }
        
        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }
    }
    
    public static class FormatDTO {
        @JsonProperty("text/html")
        private String textHtml;
        
        @JsonProperty("application/epub+zip")
        private String epubZip;
        
        @JsonProperty("application/pdf")
        private String pdf;
        
        public String getTextHtml() {
            return textHtml;
        }
        
        public void setTextHtml(String textHtml) {
            this.textHtml = textHtml;
        }
        
        public String getEpubZip() {
            return epubZip;
        }
        
        public void setEpubZip(String epubZip) {
            this.epubZip = epubZip;
        }
        
        public String getPdf() {
            return pdf;
        }
        
        public void setPdf(String pdf) {
            this.pdf = pdf;
        }
    }
} 