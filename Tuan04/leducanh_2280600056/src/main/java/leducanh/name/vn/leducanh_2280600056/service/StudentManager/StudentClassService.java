package leducanh.name.vn.leducanh_2280600056.service.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.StudentClass;
import leducanh.name.vn.leducanh_2280600056.repositories.StudentManager.StudentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class StudentClassService {
    @Autowired
    private StudentClassRepository repository;

    public List<StudentClass> findAll() {
        return repository.findAll();
    }

    public Optional<StudentClass> findById(Long id) {
        return repository.findById(id);
    }

    public StudentClass save(StudentClass studentClass) {
        return repository.save(studentClass);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
