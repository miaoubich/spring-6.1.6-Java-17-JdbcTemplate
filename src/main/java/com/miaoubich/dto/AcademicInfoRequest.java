package com.miaoubich.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.miaoubich.model.StudentStatus;

public record AcademicInfoRequest(LocalDate enrollmentDate, String program, String department, Integer yearLevel,
		StudentStatus status, BigDecimal gpa) {

}
