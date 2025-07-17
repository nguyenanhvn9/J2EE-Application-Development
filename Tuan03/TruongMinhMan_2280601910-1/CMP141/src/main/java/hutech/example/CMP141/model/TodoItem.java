package hutech.example.CMP141.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {
    private Long id;
    private String title;
    private boolean completed = false;
} 