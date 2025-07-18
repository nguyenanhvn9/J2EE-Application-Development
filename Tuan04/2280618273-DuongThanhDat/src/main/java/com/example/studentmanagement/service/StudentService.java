package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.StudentClass;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentClassRepository studentClassRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    public List<Student> searchStudents(String name) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
    
    public List<Student> getStudentsNotInClass(Long classId) {
        return studentRepository.findStudentsNotInClass(classId);
    }
    
    public void enrollStudentInClass(Long studentId, Long classId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<StudentClass> classOpt = studentClassRepository.findById(classId);
        
        if (studentOpt.isPresent() && classOpt.isPresent()) {
            Student student = studentOpt.get();
            StudentClass studentClass = classOpt.get();
            
            if (!student.getEnrolledClasses().contains(studentClass)) {
                student.getEnrolledClasses().add(studentClass);
                studentRepository.save(student);
            }
        }
    }
    
    public void removeStudentFromClass(Long studentId, Long classId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<StudentClass> classOpt = studentClassRepository.findById(classId);
        
        if (studentOpt.isPresent() && classOpt.isPresent()) {
            Student student = studentOpt.get();
            StudentClass studentClass = classOpt.get();
            
            student.getEnrolledClasses().remove(studentClass);
            studentRepository.save(student);
        }
    }
}
