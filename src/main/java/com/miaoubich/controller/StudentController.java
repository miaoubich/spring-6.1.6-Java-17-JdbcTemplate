package com.miaoubich.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.service.StudentBo;

@RestController
@RequestMapping("/students")
public class StudentController {

	private final Logger logger = LoggerFactory.getLogger(StudentController.class);
	private StudentBo studentBo;
	
	@PostMapping
	public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request) {
	    StudentResponse saved = studentBo.createStudent(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

}
