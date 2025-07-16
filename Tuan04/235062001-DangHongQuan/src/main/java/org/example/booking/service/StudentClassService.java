package org.example.booking.service;

import org.example.booking.Response.StudentClassRepository;
import org.example.booking.model.StudentClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassService {
    @Autowired
    private StudentClassRepository studentClassRepository;

    public List<StudentClass> findAll() {
        return studentClassRepository.findAll();
    }

    public StudentClass findById(Long id) {
        return studentClassRepository.findById(id).orElse(null);
    }

    public StudentClass save(StudentClass studentClass) {
        return studentClassRepository.save(studentClass);
    }

    public void delete(Long id) {
        studentClassRepository.deleteById(id);
    }
}
