package com.example.__NguyenVanTu.config;

import com.example.__NguyenVanTu.model.*;
import com.example.__NguyenVanTu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private FacultyService facultyService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem đã có dữ liệu chưa
        if (facultyService.findAll().size() > 0) {
            return; // Đã có dữ liệu rồi thì không tạo nữa
        }
        
        // Tạo Khoa
        Faculty itFaculty = facultyService.save(new Faculty("Công nghệ thông tin"));
        Faculty businessFaculty = facultyService.save(new Faculty("Quản trị kinh doanh"));
        Faculty engineeringFaculty = facultyService.save(new Faculty("Kỹ thuật"));
        
        // Tạo Môn học
        Subject javaSubject = subjectService.save(new Subject("Lập trình Java", itFaculty));
        Subject databaseSubject = subjectService.save(new Subject("Cơ sở dữ liệu", itFaculty));
        Subject webSubject = subjectService.save(new Subject("Phát triển Web", itFaculty));
        Subject managementSubject = subjectService.save(new Subject("Quản trị học", businessFaculty));
        Subject marketingSubject = subjectService.save(new Subject("Marketing", businessFaculty));
        Subject mathSubject = subjectService.save(new Subject("Toán cao cấp", engineeringFaculty));
        
        // Tạo Lớp học
        StudentClass java1Class = studentClassService.save(new StudentClass("21CTT1 - Java", javaSubject));
        StudentClass java2Class = studentClassService.save(new StudentClass("21CTT2 - Java", javaSubject));
        StudentClass db1Class = studentClassService.save(new StudentClass("21CTT1 - CSDL", databaseSubject));
        StudentClass web1Class = studentClassService.save(new StudentClass("21CTT1 - Web", webSubject));
        StudentClass mgmt1Class = studentClassService.save(new StudentClass("21QT1 - Quản trị", managementSubject));
        
        // Tạo Sinh viên
        Student student1 = studentService.save(new Student("Nguyễn", "Văn Tú", "tu.nguyen@email.com"));
        Student student2 = studentService.save(new Student("Trần", "Thị Mai", "mai.tran@email.com"));
        Student student3 = studentService.save(new Student("Lê", "Văn Nam", "nam.le@email.com"));
        Student student4 = studentService.save(new Student("Phạm", "Thị Lan", "lan.pham@email.com"));
        Student student5 = studentService.save(new Student("Hoàng", "Văn Dũng", "dung.hoang@email.com"));
        
        // Tạo một vài đăng ký trực tiếp
        // Không dùng enrollmentService để tránh vòng lặp
        System.out.println("Đã tạo dữ liệu mẫu thành công!");
    }
}
