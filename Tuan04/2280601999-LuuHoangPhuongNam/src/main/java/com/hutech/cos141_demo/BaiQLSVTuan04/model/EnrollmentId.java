package com.hutech.cos141_demo.BaiQLSVTuan04.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class EnrollmentId implements Serializable {
    private Long studentId;
    private Long studentClassId;

    public EnrollmentId() {}
    public EnrollmentId(Long studentId, Long studentClassId) {
        this.studentId = studentId;
        this.studentClassId = studentClassId;
    }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getStudentClassId() { return studentClassId; }
    public void setStudentClassId(Long studentClassId) { this.studentClassId = studentClassId; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(studentClassId, that.studentClassId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentClassId);
    }
} 