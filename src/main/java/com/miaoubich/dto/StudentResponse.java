package com.miaoubich.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentResponse(Long id, String studentNumber, String firstName, String lastName, LocalDate dateOfBirth, ContactInfoResponse contactInfoResponse, AcademicInfoResponse academicInfoResponse, 
	 LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
