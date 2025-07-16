package com.example.demo.service;

import com.example.demo.model.StudentClass;
import com.example.demo.repository.StudentClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentClassService {
    private final StudentClassRepository studentClassRepository;

    public List<StudentClass> getAllStudentClasses() {
        return studentClassRepository.findAll();
    }

    public Optional<StudentClass> getStudentClassById(Long id) {
        return studentClassRepository.findById(id);
    }

    public StudentClass saveStudentClass(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }

    public void deleteStudentClassById(Long id) {
        if (!studentClassRepository.existsById(id)) {
            throw new IllegalStateException("StudentClass with ID " + id + " does not exist.");
        }
        studentClassRepository.deleteById(id);
    }
}