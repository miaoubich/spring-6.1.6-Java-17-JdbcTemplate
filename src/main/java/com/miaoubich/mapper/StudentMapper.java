package com.miaoubich.mapper;

import org.modelmapper.ModelMapper;

import com.miaoubich.dto.ContactInfoRequest;
import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.model.ContactInfo;
import com.miaoubich.model.Student;

public class StudentMapper {
	static final ModelMapper mapper;
	static {
		mapper = new ModelMapper();
		mapper.typeMap(StudentRequest.class, Student.class)
				.addMappings(m -> m.map(StudentRequest::contactInfoRequest, Student::setContactInfo));
		mapper.typeMap(ContactInfoRequest.class, ContactInfo.class)
				.addMappings(m -> m.map(ContactInfoRequest::addressRequest, ContactInfo::setAddress));
		mapper.typeMap(StudentRequest.class, Student.class)
				.addMappings(m -> m.map(StudentRequest::academicInfoRequest, Student::setAcademicInfo));
	}

	public static Student toEntity(StudentRequest request) {
		return mapper.map(request, Student.class);
	}

	public static StudentResponse toResponse(Student student) {
		return mapper.map(student, StudentResponse.class);
	}
}
