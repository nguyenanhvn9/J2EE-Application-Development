package leducanh.name.vn.leducanh_2280600056.repositories.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Enrollment;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    // Phải truy cập qua EnrollmentId
    void deleteByIdStudentId(Long studentId);

    List<Enrollment> findByIdStudentId(Long studentId);
}
