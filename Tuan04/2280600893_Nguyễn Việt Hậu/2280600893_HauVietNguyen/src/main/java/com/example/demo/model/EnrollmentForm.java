package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EnrollmentForm {
    private UUID studentId;
    private List<UUID> enrolledClassIds;
}
