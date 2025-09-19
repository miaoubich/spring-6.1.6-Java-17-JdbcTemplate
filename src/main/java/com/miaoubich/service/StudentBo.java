package com.miaoubich.service;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;

public interface StudentBo {

	StudentResponse createStudent(StudentRequest request);
}
