package leducanh.name.vn.leducanh_2280600056.controller.StudentManager;

import leducanh.name.vn.leducanh_2280600056.dto.StudentManager.EnrollmentForm;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Enrollment;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.EnrollmentId;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Student;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.StudentClass;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.EnrollmentService;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.StudentClassService;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("student-manager/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentClassService studentClassService;

    @GetMapping
    public String showEnrollmentForm(@RequestParam(required = false) Long studentId, Model model) {
        EnrollmentForm form = new EnrollmentForm();
        form.setStudentId(studentId);

        model.addAttribute("enrollmentForm", form);
        model.addAttribute("students", studentService.findAll());

        if (studentId != null) {
            List<StudentClass> allClasses = studentClassService.findAll();
            List<StudentClass> enrolled = enrollmentService.findClassesByStudentId(studentId);

            List<StudentClass> available = allClasses.stream()
                    .filter(c -> !enrolled.contains(c))
                    .toList();

            model.addAttribute("availableClasses", available);
            model.addAttribute("enrolledClasses", enrolled);
        }

        return "StudentManager/enrollment";
    }


    @PostMapping("/save")
    public String saveEnrollment(@RequestParam Long studentId,
                                 @RequestParam(name = "enrolledClassIds", required = false) List<Long> enrolledClassIds) {
        // Nếu không chọn lớp nào thì truyền danh sách rỗng vào để xoá hết
        enrollmentService.updateEnrollments(studentId, enrolledClassIds != null ? enrolledClassIds : List.of());
        return "redirect:/student-manager/enrollments?studentId=" + studentId;
    }


    @PostMapping("/add")
    public String addEnrollment(@ModelAttribute Enrollment enrollment) {
        enrollmentService.save(enrollment);
        return "redirect:/student-manager/enrollments";
    }

    @PostMapping("/edit")
    public String editEnrollment(@RequestParam Long studentId,
                                 @RequestParam Long classId,
                                 @ModelAttribute Enrollment enrollment) {
        enrollment.setId(new EnrollmentId(studentId, classId));
        enrollmentService.save(enrollment);
        return "redirect:/student-manager/enrollments?studentId=" + studentId;
    }


    @PostMapping("/edit/{id}")
    public String editEnrollment(@PathVariable EnrollmentId id, @ModelAttribute Enrollment enrollment) {
        enrollment.setId(id);
        enrollmentService.save(enrollment);
        return "redirect:/student-manager/enrollments";
    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable EnrollmentId id) {
        enrollmentService.deleteById(id);
        return "redirect:/student-manager/enrollments";
    }
}