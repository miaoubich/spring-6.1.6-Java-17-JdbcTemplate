package com.miaoubich.service.dao;

import java.util.List;

import com.miaoubich.model.Student;

public interface StudentDao {

	Student saveNewStudent(Student student);

	List<Student> saveStudents(List<Student> students);

	Student getStudentByStudentNumber(String studentNumber);
}
