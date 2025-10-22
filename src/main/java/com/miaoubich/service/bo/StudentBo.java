package com.miaoubich.service.bo;

import java.util.Optional;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;

public interface StudentBo {

	StudentResponse createStudent(StudentRequest request);

	Optional<StudentResponse> findStudentByStudentNumber(String studentNumber);
}
