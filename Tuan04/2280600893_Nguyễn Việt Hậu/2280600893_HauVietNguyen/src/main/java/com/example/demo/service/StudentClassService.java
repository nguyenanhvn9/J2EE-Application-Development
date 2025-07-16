package com.example.demo.service;

import com.example.demo.model.StudentClass;
import com.example.demo.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class StudentClassService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    public List<StudentClass> findAll() {
        return studentClassRepository.findAll();
    }

    public List<StudentClass> findBySubjectId(UUID subjectId) {
        return studentClassRepository.findBySubjectId(subjectId);
    }

    public StudentClass findById(UUID id) {
        return studentClassRepository.findById(id).orElse(null);
    }

    public StudentClass save(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }

    public void deleteById(UUID id) {
        studentClassRepository.deleteById(id);
    }
}
