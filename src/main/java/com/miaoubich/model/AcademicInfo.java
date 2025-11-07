package com.miaoubich.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AcademicInfo {
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate enrollmentDate;
	private String program;
	private String department;
	private Integer yearLevel;
	private StudentStatus studentStatus;
	private BigDecimal gpa;

	public AcademicInfo() {}

	public AcademicInfo(LocalDate enrollmentDate, String program, String department, Integer yearLevel,
			StudentStatus studentStatus, BigDecimal gpa) {
		super();
		this.enrollmentDate = enrollmentDate;
		this.program = program;
		this.department = department;
		this.yearLevel = yearLevel;
		this.studentStatus = studentStatus;
		this.gpa = gpa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public void setStudentStatus(StudentStatus status) {
		this.studentStatus = status;
	}

	public BigDecimal getGpa() {
		return gpa;
	}

	public void setGpa(BigDecimal gpa) {
		this.gpa = gpa;
	}

	@Override
	public String toString() {
		return "AcademicInfo [id=" + id + ", enrollmentDate=" + enrollmentDate + ", program=" + program
				+ ", department=" + department + ", yearLevel=" + yearLevel + ", studentStatus=" + studentStatus + ", gpa=" + gpa
				+ "]";
	}
}
