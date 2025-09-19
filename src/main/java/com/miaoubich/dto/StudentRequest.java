package com.miaoubich.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.miaoubich.model.AcademicInfo;
import com.miaoubich.model.ContactInfo;
import com.miaoubich.model.Gender;

public record StudentRequest(String studentNumber, String firstName, String lastName, LocalDate dateOfBirth,
		 LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
