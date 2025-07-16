package leducanh.name.vn.leducanh_2280600056.service.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Faculty;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Subject;
import leducanh.name.vn.leducanh_2280600056.repositories.StudentManager.FacultyRepository;
import leducanh.name.vn.leducanh_2280600056.repositories.StudentManager.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private FacultyRepository facultyRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject save(Subject subject) {
        Faculty faculty = facultyRepository.findById(subject.getFaculty().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid facultyId: " + subject.getFaculty().getId()));
        subject.setFaculty(faculty);
        return subjectRepository.save(subject);
    }

    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }
}
