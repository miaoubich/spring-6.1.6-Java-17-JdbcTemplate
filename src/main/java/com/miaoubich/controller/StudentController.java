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

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/students")
public class StudentController {

	private final Logger logger = LoggerFactory.getLogger(StudentController.class);
	private StudentBo studentBo;
	
	public StudentController(StudentBo studentBo) {
		this.studentBo = studentBo;
	}
	
	@PostMapping
	public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request) {
	    StudentResponse saved = studentBo.upsertStudent(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/{studentNumber}")
	public ResponseEntity<StudentResponse> getStudentByStudentNumber(@PathVariable String studentNumber) {
	    Optional<StudentResponse> student = studentBo.findStudentByStudentNumber(studentNumber);
	    return (student.isEmpty()) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build(): ResponseEntity.status(HttpStatus.FOUND).body(student.get());
	}

	@PatchMapping("/{studentNumber}")
	public ResponseEntity<StudentResponse> updateStudentStatus(@PathVariable String studentNumber, @RequestParam String status) {
	    StudentResponse updated = studentBo.updateStudentStatus(studentNumber, Enum.valueOf(StudentStatus.class, status.toUpperCase()));
	    return ResponseEntity.ok(updated);
	}

	@PutMapping
	public ResponseEntity<StudentResponse> updateStudent(@RequestBody StudentRequest request) {
		StudentResponse saved = studentBo.upsertStudent(request);
		return ResponseEntity.status(HttpStatus.OK).body(saved);
	}

	@GetMapping
	public ResponseEntity<List<StudentResponse>> getAllStudents() {
	    List<StudentResponse> students = studentBo.getAllStudents();
	    return ResponseEntity.ok(students);
	}
}
