package com.miaoubich.service.bo;

import org.springframework.stereotype.Service;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.mapper.StudentMapper;
import com.miaoubich.model.Student;
import com.miaoubich.service.dao.StudentDao;

@Service
public class StudentBoImpl implements StudentBo {

	private StudentMapper studentMapper;
	private StudentDao studentDao;
	
	public StudentBoImpl(StudentMapper studentMapper, StudentDao studentDao) {
		this.studentMapper = studentMapper;
		this.studentDao = studentDao;
	}
	
	@Override
	public StudentResponse createStudent(StudentRequest request) {
		StudentResponse response = null;
		
		if(request != null) {
			Student student = studentMapper.toEntity(request);
			studentDao.saveNewStudent(student);
			
			return studentMapper.toResponse(student);
		}		
		return response;
	}

}
