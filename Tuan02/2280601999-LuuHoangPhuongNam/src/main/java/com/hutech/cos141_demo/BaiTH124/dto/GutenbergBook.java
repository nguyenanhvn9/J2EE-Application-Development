package com.hutech.cos141_demo.BaiTH124.dto;

import java.util.List;

import lombok.Data;

@Data
public class GutenbergBook {
    private int id;
    private String title;
    private List<GutenbergAuthor> authors;
    private List<String> languages;
    private int download_count;
    private List<String> subjects;
    private List<String> bookshelves;
    private GutenbergFormats formats;
}

 