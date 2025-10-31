package com.miaoubich.service.bo;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.model.StudentStatus;

import java.util.List;
import java.util.Optional;

public interface StudentBo {

	StudentResponse upsertStudent(StudentRequest request);

	Optional<StudentResponse> findStudentByStudentNumber(String studentNumber);

    StudentResponse updateStudentStatus(String studentNumber, StudentStatus studentStatus);

    List<StudentResponse> getAllStudents();
}
