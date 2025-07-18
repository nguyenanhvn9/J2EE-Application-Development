package com.example.studentmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.studentmanagement.model.Faculty;
import com.example.studentmanagement.repository.FacultyRepository;
import com.example.studentmanagement.repository.StudentClassRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.SubjectRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Tạo dữ liệu mẫu đơn giản
        if (facultyRepository.count() == 0) {
            // Tạo Khoa
            Faculty itFaculty = new Faculty("Công nghệ Thông tin");
            Faculty businessFaculty = new Faculty("Quản trị Kinh doanh");

            facultyRepository.save(itFaculty);
            facultyRepository.save(businessFaculty);

            System.out.println("=== DỮ LIỆU MẪU ĐÃ ĐƯỢC TẠO ===");
            System.out.println("Khoa: " + facultyRepository.count());
        }
    }
}
