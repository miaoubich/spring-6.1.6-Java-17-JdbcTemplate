package com.miaoubich.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.model.Student;

@Mapper(componentModel = "spring", 
uses = {AddressMapper.class, 
		ContactInfoMapper.class, 
		AcademicInfoMapper.class})
public interface StudentMapper {
	
	@Mapping(source = "contactInfoRequest", target = "contactInfo")
    @Mapping(source = "academicInfoRequest", target = "academicInfo")
	Student toEntity(StudentRequest request);
	StudentResponse toResponse(Student student);
}
