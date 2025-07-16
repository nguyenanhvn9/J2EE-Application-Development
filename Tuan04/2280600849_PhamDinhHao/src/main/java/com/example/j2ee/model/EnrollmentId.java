package com.example.j2ee.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class EnrollmentId implements Serializable {
    private Long studentId;
    private Long classId;

    public EnrollmentId() {}

    public EnrollmentId(Long studentId, Long classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentId)) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, classId);
    }
}