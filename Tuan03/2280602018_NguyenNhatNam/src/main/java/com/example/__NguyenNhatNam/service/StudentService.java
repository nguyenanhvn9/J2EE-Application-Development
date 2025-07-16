package com.example.__NguyenNhatNam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.__NguyenNhatNam.model.Student;

@Service
public class StudentService {
    private List<Student> students = new ArrayList<>();
    private Long nextId = 1L;

    public List<Student> getAllStudents() {
        return students;
    }

    public void addStudent(Student student) {
        student.setId(nextId++);
        students.add(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public void updateStudent(Student updatedStudent) {
        students.stream()
                .filter(s -> s.getId().equals(updatedStudent.getId()))
                .findFirst()
                .ifPresent(s -> {
                    s.setName(updatedStudent.getName());
                    s.setEmail(updatedStudent.getEmail());
                });
    }

    public void deleteStudent(Long id) {
        students.removeIf(s -> s.getId().equals(id));
    }
}
