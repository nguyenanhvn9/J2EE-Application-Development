package com.hutech.cos141_demo.BaiTH124.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutenbergResponse {
    private int count;
    private String next;
    private String previous;
    private List<GutenbergBook> results;
} 