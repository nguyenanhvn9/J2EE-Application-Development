package com.__TranThanhDat.__TranThanhDat.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookApiResponse {
    private List<BookDTO> results;
}
