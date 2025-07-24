package org.example.booking.Response;

import org.example.booking.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // Tùy chọn thêm các phương thức tìm kiếm nếu cần
}
