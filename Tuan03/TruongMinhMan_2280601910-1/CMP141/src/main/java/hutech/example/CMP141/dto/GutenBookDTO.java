package hutech.example.CMP141.dto;

import lombok.Data;
import java.util.List;

@Data
public class GutenBookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;
} 