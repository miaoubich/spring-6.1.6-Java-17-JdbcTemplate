package com.miaoubich.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
	private Long id;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;

    private ContactInfo contactInfo; // Composition
    private AcademicInfo academicInfo; // Composition

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Student() {}

	public Student(String studentNumber, String firstName, String lastName, LocalDate dateOfBirth, Gender gender,
			ContactInfo contactInfo, AcademicInfo academicInfo, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.studentNumber = studentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.contactInfo = contactInfo;
		this.academicInfo = academicInfo;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public AcademicInfo getAcademicInfo() {
		return academicInfo;
	}

	public void setAcademicInfo(AcademicInfo academicInfo) {
		this.academicInfo = academicInfo;
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
		return "Student [id=" + id + ", studentNumber=" + studentNumber + ", firstName=" + firstName + ", lastName="
				+ lastName + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", contactInfo=" + contactInfo
				+ ", academicInfo=" + academicInfo + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
