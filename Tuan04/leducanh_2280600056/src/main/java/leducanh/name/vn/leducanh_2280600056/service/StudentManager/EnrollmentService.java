package leducanh.name.vn.leducanh_2280600056.service.StudentManager;

import jakarta.transaction.Transactional;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Enrollment;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.EnrollmentId;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.StudentClass;
import leducanh.name.vn.leducanh_2280600056.repositories.StudentManager.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentClassService studentClassService;

    public List<StudentClass> findClassesByStudentId(Long studentId) {
        return enrollmentRepository.findByIdStudentId(studentId)
                .stream()
                .map(Enrollment::getStudentClass)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateEnrollments(Long studentId, List<Long> newClassIds) {
        if (studentId == null || newClassIds == null) return;

        enrollmentRepository.deleteByIdStudentId(studentId);

        for (Long classId : newClassIds) {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(new EnrollmentId(studentId, classId));
            enrollment.setStudent(studentService.findById(studentId).orElseThrow());
            enrollment.setStudentClass(studentClassService.findById(classId).orElseThrow());
            enrollmentRepository.save(enrollment);
        }
    }

    public Optional<Enrollment> findByStudentId(Long id){
        return enrollmentRepository.findByIdStudentId(id).stream().findFirst();
}
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(EnrollmentId id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteById(EnrollmentId id) {
        enrollmentRepository.deleteById(id);
    }
}
