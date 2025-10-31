package com.miaoubich.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setZipCode(rs.getString("zip_code"));
        address.setCountry(rs.getString("country"));

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(rs.getString("email"));
        contactInfo.setPhoneNumber(rs.getString("phone_number"));
        contactInfo.setAddress(address);

        AcademicInfo academicInfo = new AcademicInfo();
        Date enrollmentDate = rs.getDate("enrollment_date");
        if(enrollmentDate != null) {
            academicInfo.setEnrollmentDate(enrollmentDate.toLocalDate());
        }
        academicInfo.setProgram(rs.getString("program"));
        academicInfo.setDepartment(rs.getString("department"));
        academicInfo.setYearLevel(rs.getInt("year_level"));
        String studentStatus = rs.getString("status");
        if(studentStatus != null) {
            academicInfo.setStudentStatus(StudentStatus.valueOf(studentStatus));
        }
        academicInfo.setGpa(rs.getBigDecimal("gpa"));

        Student student = new Student();
        student.setId(rs.getLong("student_id"));
        student.setStudentNumber(rs.getString("student_number"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        Date dateOfBirth = rs.getDate("date_of_birth");
        if(dateOfBirth != null) {
            student.setDateOfBirth(dateOfBirth.toLocalDate());
        }
        student.setGender(Gender.valueOf(rs.getString("gender")));
        student.setContactInfo(contactInfo);
        student.setAcademicInfo(academicInfo);
        var createdAt = rs.getTimestamp("created_at");
        if(createdAt != null) {
            student.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if(updatedAt != null) {
            student.setCreatedAt(updatedAt.toLocalDateTime());
        }
        return student;
    }
}
