package org.example.booking.service;

import org.example.booking.model.StudentCrud;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentCrudService {

    private final Map<Long, StudentCrud> store = new HashMap<>();
    private Long idCounter = 1L;

    public List<StudentCrud> getAll() {
        return new ArrayList<>(store.values());
    }

    public StudentCrud getById(Long id) {
        return store.get(id);
    }

    public StudentCrud create(StudentCrud student) {
        student.setId(idCounter++);
        store.put(student.getId(), student);
        return student;
    }

    public StudentCrud update(Long id, StudentCrud updatedStudent) {
        StudentCrud student = store.get(id);
        if (student != null) {
            student.setMssv(updatedStudent.getMssv());
            student.setTen(updatedStudent.getTen());
        }
        return student;
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
