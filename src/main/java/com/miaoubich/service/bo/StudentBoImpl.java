package com.miaoubich.service.bo;

import org.springframework.stereotype.Service;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.mapper.StudentMapper;
import com.miaoubich.model.Student;

@Service
public class StudentBoImpl implements StudentBo {

	private StudentMapper studentMapper;
	
	public StudentBoImpl(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}
	
	@Override
	public StudentResponse createStudent(StudentRequest request) {
		String sql = "";
		
		if(request != null) {
			Student student = studentMapper.toEntity(request);
			
		}		
		
		return null;
	}

}
