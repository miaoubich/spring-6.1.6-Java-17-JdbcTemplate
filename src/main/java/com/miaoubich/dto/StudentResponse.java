package com.miaoubich.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentResponse {

	private Long id;
	private String studentNumber;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private ContactInfoResponse contactInfoResponse;
	private AcademicInfoResponse academicInfoResponse;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// No‑argument constructor (required by ModelMapper)
	public StudentResponse() {}

	// All‑arguments constructor (optional, convenient for manual creation)
	public StudentResponse(Long id, String studentNumber, String firstName, String lastName,
						   LocalDate dateOfBirth, ContactInfoResponse contactInfoResponse,
						   AcademicInfoResponse academicInfoResponse, LocalDateTime createdAt,
						   LocalDateTime updatedAt) {
		this.id = id;
		this.studentNumber = studentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.contactInfoResponse = contactInfoResponse;
		this.academicInfoResponse = academicInfoResponse;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// --- Getters and Setters ---
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public ContactInfoResponse getContactInfoResponse() {
		return contactInfoResponse;
	}

	public void setContactInfoResponse(ContactInfoResponse contactInfoResponse) {
		this.contactInfoResponse = contactInfoResponse;
	}

	public AcademicInfoResponse getAcademicInfoResponse() {
		return academicInfoResponse;
	}

	public void setAcademicInfoResponse(AcademicInfoResponse academicInfoResponse) {
		this.academicInfoResponse = academicInfoResponse;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "StudentResponse{" +
				"id=" + id +
				", studentNumber='" + studentNumber + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", contactInfoResponse=" + contactInfoResponse +
				", academicInfoResponse=" + academicInfoResponse +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}