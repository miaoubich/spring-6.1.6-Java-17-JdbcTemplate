package com.miaoubich.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.miaoubich.model.StudentStatus;

public record AcademicInfoResponse(
		LocalDate enrollmentDate, 
		String program, String department, 
		Integer yearLevel,
		StudentStatus studentStatus, 
		BigDecimal gpa) {

}
