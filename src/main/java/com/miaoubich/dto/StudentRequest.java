package com.miaoubich.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentRequest(String studentNumber, String firstName, String lastName, LocalDate dateOfBirth,
		ContactInfoRequest contactInfoRequest, AcademicInfoRequest academicInfoRequest, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
