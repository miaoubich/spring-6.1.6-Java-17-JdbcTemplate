package com.miaoubich.service.bo;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.mapper.StudentMapper;
import com.miaoubich.model.Student;
import com.miaoubich.service.dao.StudentDao;


@Service
public class StudentBoImpl implements StudentBo {

	private static final Logger logger = LoggerFactory.getLogger(StudentBoImpl.class);
	private StudentDao studentDao;
	
	public StudentBoImpl(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	@Override
	public StudentResponse createStudent(StudentRequest request) {
		logger.info("Creating student: {}", request);
		
		StudentResponse response = null;
		Student student = null;
		
		if(request != null) {
			student = new Student();
			student = StudentMapper.toEntity(request);
			studentDao.saveNewStudent(student);
			response = StudentMapper.toResponse(student);
		}		
		return response;
	}
	
	@Override
	public Optional<StudentResponse> findStudentByStudentNumber(String studentNumber) {
		logger.info("Finding student by student number: {}", studentNumber);
	    // Fetch entity from DAO
	    Student student = studentDao.getStudentByStudentNumber(studentNumber);

	    if (student == null) {
	        logger.warn("No student found with student number: {}", studentNumber);
	        return Optional.empty();
	    }
	    return Optional.of(StudentMapper.toResponse(student));
	}

/*
	private Student toEntity(StudentRequest request) {
		Student student = new Student();
		Address address = new Address();
		ContactInfo contactInfo = new ContactInfo();
		AcademicInfo academicInfo = new AcademicInfo();
		
		BeanUtils.copyProperties(request.contactInfoRequest().addressRequest(), address);
		BeanUtils.copyProperties(request.contactInfoRequest(), contactInfo);
		BeanUtils.copyProperties(request.academicInfoRequest(), academicInfo);
		
		contactInfo.setAddress(address);
		student.setContactInfo(contactInfo);
		student.setAcademicInfo(academicInfo);
		BeanUtils.copyProperties(request, student);
		
		return student;
	}
	
	private StudentResponse toResponse(Student student) {
	    if (student == null) return null;

	    // Build nested responses
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

	    // Now call the canonical record constructor in the right order
	    return new StudentResponse(
	        student.getId(),
	        student.getStudentNumber(),
	        student.getFirstName(),
	        student.getLastName(),
	        student.getDateOfBirth(),
	        contactInfoResponse,
	        academicInfoResponse,
	        student.getCreatedAt(),
	        student.getUpdatedAt()
	    );
	}
	*/
}
