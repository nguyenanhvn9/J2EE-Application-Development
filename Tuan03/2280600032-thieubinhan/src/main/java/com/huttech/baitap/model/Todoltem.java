package com.huttech.baitap.model;

import lombok.Data;

@Data
public class Todoltem {
    private Long id;
    private String title;
    private boolean completed;

    public Todoltem(Long id, String title) {
        this.id = id;
        this.title = title;
        this.completed = false;
    }
}
