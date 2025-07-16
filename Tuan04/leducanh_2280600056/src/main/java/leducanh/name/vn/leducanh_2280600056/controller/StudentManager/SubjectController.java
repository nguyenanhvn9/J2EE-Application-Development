package leducanh.name.vn.leducanh_2280600056.controller.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Faculty;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Subject;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.FacultyService;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("student-manager/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private FacultyService facultyService;

    // Hiển thị danh sách và form thêm mới
    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("subject", new Subject()); // để form trống
        model.addAttribute("faculties", facultyService.findAll());
        return "StudentManager/subject";
    }

    @GetMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id).orElseThrow();
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("subject", subject); // để hiển thị lên form
        model.addAttribute("faculties", facultyService.findAll());
        return "StudentManager/subject";
    }

    // Lưu môn học mới
    @PostMapping("/add")
    public String addSubject(@ModelAttribute Subject subject) {
        subjectService.save(subject);
        return "redirect:/student-manager/subjects";
    }


    // Cập nhật môn học
    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id, @ModelAttribute Subject subject) {
        Subject existing = subjectService.findById(id).orElseThrow();
        existing.setName(subject.getName());
        existing.setFaculty(subject.getFaculty());
        subjectService.save(existing);
        return "redirect:/student-manager/subjects";
    }

    // Xóa môn học
    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteById(id);
        return "redirect:/student-manager/subjects";
    }
}
