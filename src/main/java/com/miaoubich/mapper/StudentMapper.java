package com.miaoubich.mapper;

import com.miaoubich.dto.*;
import com.miaoubich.model.AcademicInfo;
import com.miaoubich.model.Address;
import com.miaoubich.model.ContactInfo;
import com.miaoubich.model.Student;
import org.springframework.beans.BeanUtils;

public class StudentMapper {

    public static Student toEntity(StudentRequest request) {
        if (request == null) return null;

        Student student = new Student();
        Address address = new Address();
        ContactInfo contactInfo = new ContactInfo();
        AcademicInfo academicInfo = new AcademicInfo();

        BeanUtils.copyProperties(request.contactInfoRequest().addressRequest(), address);
        BeanUtils.copyProperties(request.contactInfoRequest(), contactInfo);
        BeanUtils.copyProperties(request.academicInfoRequest(), academicInfo);
        BeanUtils.copyProperties(request, student);

        contactInfo.setAddress(address);
        student.setContactInfo(contactInfo);
        student.setAcademicInfo(academicInfo);
        return student;
    }

    public static StudentResponse toResponse(Student student) {
        if (student == null) return null;

        AddressResponse addressResponse = null;
        if (student.getContactInfo() != null && student.getContactInfo().getAddress() != null) {
            Address address = student.getContactInfo().getAddress();
            addressResponse = new AddressResponse(
                    address.getStreet(),
                    address.getCity(),
                    address.getZipCode(),
                    address.getCountry()
            );
        }

        ContactInfoResponse contactInfoResponse = null;
        if (student.getContactInfo() != null) {
            ContactInfo contactInfo = student.getContactInfo();
            contactInfoResponse = new ContactInfoResponse(
                    contactInfo.getEmail(),
                    contactInfo.getPhoneNumber(),
                    addressResponse
            );
        }

        AcademicInfoResponse academicInfoResponse = null;
        if (student.getAcademicInfo() != null) {
            AcademicInfo academicInfo = student.getAcademicInfo();
            academicInfoResponse = new AcademicInfoResponse(
                    academicInfo.getEnrollmentDate(),
                    academicInfo.getProgram(),
                    academicInfo.getDepartment(),
                    academicInfo.getYearLevel(),
                    academicInfo.getStudentStatus(),
                    academicInfo.getGpa()
            );
        }

        return new StudentResponse(
                student.getId(),
                student.getStudentNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getDateOfBirth(),
                student.getGender(),
                contactInfoResponse,
                academicInfoResponse,
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }
}
