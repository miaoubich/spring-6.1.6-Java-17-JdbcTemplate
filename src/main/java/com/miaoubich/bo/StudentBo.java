package com.miaoubich.bo;

import com.miaoubich.records.StudentRequest;
import com.miaoubich.records.StudentResponse;

public interface StudentBo {

	StudentResponse createStudent(StudentRequest request);
}
