package com.garage.garage_backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookDTO {
    private int id;
    private String title;

    @JsonProperty("authors")
    private List<AuthorDTO> authors;
}
