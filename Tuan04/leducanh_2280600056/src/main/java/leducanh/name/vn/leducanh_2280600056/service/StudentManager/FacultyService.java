package leducanh.name.vn.leducanh_2280600056.service.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Faculty;
import leducanh.name.vn.leducanh_2280600056.repositories.StudentManager.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }

    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteById(Long id) {
        facultyRepository.deleteById(id);
    }
}