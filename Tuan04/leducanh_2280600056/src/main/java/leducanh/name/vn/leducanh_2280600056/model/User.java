package leducanh.name.vn.leducanh_2280600056.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user") // nên để tên bảng thường
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String username;
    private String email;
    private String phone;

    // Không nên thêm getter/setter thủ công nếu đã dùng Lombok
}
