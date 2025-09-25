package com.miaoubich.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miaoubich.model.Gender;

public record StudentRequest(
		String studentNumber, 
		String firstName, 
		String lastName, 
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate dateOfBirth,
		Gender gender,
		ContactInfoRequest contactInfoRequest, 
		AcademicInfoRequest academicInfoRequest, 
		LocalDateTime createdAt, 
		LocalDateTime updatedAt) {
}
