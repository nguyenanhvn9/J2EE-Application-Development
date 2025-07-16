package leducanh.name.vn.leducanh_2280600056.controller.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.StudentClass;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.StudentClassService;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("student-manager/classes")
public class StudentClassController {

    @Autowired
    private StudentClassService classService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String listClasses(Model model) {
        model.addAttribute("classes", classService.findAll());
        model.addAttribute("studentClass", new StudentClass());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("actionUrl", "/student-manager/classes/add");
        return "StudentManager/class";
    }

    @PostMapping("/add")
    public String addClass(@ModelAttribute StudentClass studentClass) {
        classService.save(studentClass);
        return "redirect:/student-manager/classes";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        StudentClass studentClass = classService.findById(id).orElseThrow();
        model.addAttribute("studentClass", studentClass);
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("classes", classService.findAll());
        model.addAttribute("actionUrl", "/student-manager/classes/edit/" + id);
        return "StudentManager/class";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute StudentClass studentClass) {
        studentClass.setId(id);
        classService.save(studentClass);
        return "redirect:/student-manager/classes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        classService.deleteById(id);
        return "redirect:/student-manager/classes";
    }
}
