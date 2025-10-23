package com.miaoubich.dto;

import com.miaoubich.model.StudentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AcademicInfoResponse {

	private LocalDate enrollmentDate;
	private String program;
	private String department;
	private Integer yearLevel;
	private StudentStatus studentStatus;
	private BigDecimal gpa;

	// No-argument constructor (required by ModelMapper)
	public AcademicInfoResponse() {}

	// All-arguments constructor (for convenience)
	public AcademicInfoResponse(LocalDate enrollmentDate, String program, String department,
								Integer yearLevel, StudentStatus studentStatus, BigDecimal gpa) {
		this.enrollmentDate = enrollmentDate;
		this.program = program;
		this.department = department;
		this.yearLevel = yearLevel;
		this.studentStatus = studentStatus;
		this.gpa = gpa;
	}

	// Getters and Setters
	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getYearLevel() {
		return yearLevel;
	}

	public void setYearLevel(Integer yearLevel) {
		this.yearLevel = yearLevel;
	}

	public StudentStatus getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(StudentStatus studentStatus) {
		this.studentStatus = studentStatus;
	}

	public BigDecimal getGpa() {
		return gpa;
	}

	public void setGpa(BigDecimal gpa) {
		this.gpa = gpa;
	}

	@Override
	public String toString() {
		return "AcademicInfoResponse{" +
				"enrollmentDate=" + enrollmentDate +
				", program='" + program + '\'' +
				", department='" + department + '\'' +
				", yearLevel=" + yearLevel +
				", studentStatus=" + studentStatus +
				", gpa=" + gpa +
				'}';
	}
}
