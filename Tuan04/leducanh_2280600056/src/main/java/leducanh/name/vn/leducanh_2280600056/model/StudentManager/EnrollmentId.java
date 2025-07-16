package leducanh.name.vn.leducanh_2280600056.model.StudentManager;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnrollmentId implements Serializable {
    private Long studentId;
    private Long studentClassId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentId)) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(studentClassId, that.studentClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentClassId);
    }
}
