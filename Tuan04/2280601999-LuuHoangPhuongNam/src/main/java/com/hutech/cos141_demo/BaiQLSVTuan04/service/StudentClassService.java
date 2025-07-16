package com.hutech.cos141_demo.BaiQLSVTuan04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hutech.cos141_demo.BaiQLSVTuan04.model.StudentClass;
import com.hutech.cos141_demo.BaiQLSVTuan04.repository.StudentClassRepository;

@Service
public class StudentClassService {
    @Autowired
    private StudentClassRepository studentClassRepository;

    public List<StudentClass> getAllStudentClasses() {
        return studentClassRepository.findAll();
    }

    public Optional<StudentClass> getStudentClassById(Long id) {
        return studentClassRepository.findById(id);
    }

    public StudentClass saveStudentClass(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }

    public void deleteStudentClass(Long id) {
        studentClassRepository.deleteById(id);
    }
} 