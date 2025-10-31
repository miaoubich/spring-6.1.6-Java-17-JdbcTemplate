package com.miaoubich.service.bo;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.exception.StudentNotFoundException;
import com.miaoubich.mapper.StudentMapper;
import com.miaoubich.model.Student;
import com.miaoubich.model.StudentStatus;
import com.miaoubich.service.dao.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentBoImpl implements StudentBo {

	private static final Logger logger = LoggerFactory.getLogger(StudentBoImpl.class);
	private final StudentDao studentDao;
	
	public StudentBoImpl(StudentDao studentDao) {

		this.studentDao = studentDao;
	}
	
	@Override
	public StudentResponse upsertStudent(StudentRequest request) {
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

	@Override
	public StudentResponse updateStudentStatus(String studentNumber, StudentStatus studentStatus) {
		logger.info("Updating status of the student with student-number: {}", studentNumber);
		Student student = studentDao.updateStudentByStudentStatus(studentNumber, studentStatus);
		if (student == null)
			throw new StudentNotFoundException("Student with number " + studentNumber +
					" not found. Status update to " + studentStatus + " aborted.", HttpStatus.NOT_FOUND);;
		return StudentMapper.toResponse(student);
	}

	@Override
	public List<StudentResponse> getAllStudents() {
		logger.info("Fetching all students");
		List<Student> students = studentDao.getAllStudents();
		return students.stream().map(StudentMapper::toResponse).toList();
	}

}
