package com.miaoubich.mapper;

import org.mapstruct.Mapper;

import com.miaoubich.model.Student;
import com.miaoubich.records.StudentRequest;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	Student toEntity(StudentRequest request);
	StudentRequest toRequest(Student student);
}
