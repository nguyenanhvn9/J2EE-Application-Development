package org.example.booking.service;

import org.example.booking.Response.EnrollmentRepository;
import org.example.booking.model.Enrollment;
import org.example.booking.model.Student;
import org.example.booking.model.StudentClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentService studentService;

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public void enrollStudent(StudentClass studentClass, Student student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentClass(studentClass);
        enrollment.setStudent(student);
        enrollmentRepository.save(enrollment);
    }

    public void unenrollStudent(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    public List<Student> findEnrolledStudents(StudentClass studentClass) {
        // Lấy danh sách sinh viên đã đăng ký vào lớp học
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream()
                .filter(enrollment -> enrollment.getStudentClass().getId().equals(studentClass.getId()))
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
    }

    public List<Student> findNotEnrolledStudents(StudentClass studentClass) {
        // Lấy danh sách tất cả sinh viên
        List<Student> allStudents = studentService.findAll();

        // Lấy danh sách sinh viên đã đăng ký vào lớp học
        List<Student> enrolledStudents = findEnrolledStudents(studentClass);

        // Lọc ra những sinh viên chưa đăng ký
        return allStudents.stream()
                .filter(student -> !enrolledStudents.contains(student))
                .collect(Collectors.toList());
    }
}
