/*
package com.miaoubich.mapper;

import com.miaoubich.dto.*;
import com.miaoubich.model.*;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.record.RecordModule;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

	private final ModelMapper mapper;

	public StudentMapper() {
		mapper = new ModelMapper();
		mapper.registerModule(new RecordModule());

		// Enable deeper automatic matching
		mapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
				.setPropertyCondition(Conditions.isNotNull());

		// Guarantee nested destination objects exist before mapping
		TypeMap<StudentRequest, Student> studentTypeMap = mapper.createTypeMap(StudentRequest.class, Student.class);
		studentTypeMap.setPreConverter(ctx -> {
			Student dest = ctx.getDestination();
			if (dest.getContactInfo() == null) dest.setContactInfo(new ContactInfo());
			if (dest.getAcademicInfo() == null) dest.setAcademicInfo(new AcademicInfo());
			return dest;
		});

		TypeMap<Student, StudentResponse> typeMap =
				mapper.createTypeMap(Student.class, StudentResponse.class);
		typeMap.addMapping(Student::getContactInfo, StudentResponse::setContactInfoResponse);
		typeMap.addMapping(Student::getAcademicInfo, StudentResponse::setAcademicInfoResponse);


		// Optional nested mapping for ContactInfo → Address
		TypeMap<ContactInfoRequest, ContactInfo> contactInfoTypeMap =
				mapper.createTypeMap(ContactInfoRequest.class, ContactInfo.class);
		contactInfoTypeMap.setPreConverter(ctx -> {
			ContactInfo dest = ctx.getDestination();
			if (dest.getAddress() == null) dest.setAddress(new Address());
			return dest;
		});

		TypeMap<ContactInfo, ContactInfoResponse> contactInfoResponsetypeMap =
				mapper.createTypeMap(ContactInfo.class, ContactInfoResponse.class);
		contactInfoResponsetypeMap.setPreConverter(ctx -> {
			ContactInfoResponse dest = ctx.getDestination();
			if(dest.getAddressResponse() == null) dest.setAddressResponse(new AddressResponse());
			return dest;
		});
	}

	public Student toEntity(StudentRequest request) {
		return mapper.map(request, Student.class);
	}

	public StudentResponse toResponse(Student student) {
		return mapper.map(student, StudentResponse.class);
	}
}
*/
