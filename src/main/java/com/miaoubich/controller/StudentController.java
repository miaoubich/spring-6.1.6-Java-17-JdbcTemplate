package com.miaoubich.controller;

import com.miaoubich.dto.StudentRequest;
import com.miaoubich.dto.StudentResponse;
import com.miaoubich.model.StudentStatus;
import com.miaoubich.service.bo.StudentBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

	private final Logger logger = LoggerFactory.getLogger(StudentController.class);
	private StudentBo studentBo;
	
	public StudentController(StudentBo studentBo) {
		this.studentBo = studentBo;
	}
	
	@PostMapping
	public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request) {
	    StudentResponse saved = studentBo.createStudent(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/{studentNumber}")
	public ResponseEntity<StudentResponse> getStudentByStudentNumber(@PathVariable String studentNumber) {
	    Optional<StudentResponse> student = studentBo.findStudentByStudentNumber(studentNumber);
	    return (student.isEmpty()) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build(): ResponseEntity.ok(student.get());
	}

	@PatchMapping("/{studentNumber}")
	public ResponseEntity<StudentResponse> updateStudentStatus(@PathVariable String studentNumber, @RequestParam String status) {
	    StudentResponse updated = studentBo.updateStudentStatus(studentNumber, Enum.valueOf(StudentStatus.class, status.toUpperCase()));
	    return ResponseEntity.ok(updated);
	}
}
