package com.miaoubich.service.dao;

import com.miaoubich.model.Student;
import com.miaoubich.model.StudentStatus;

import java.util.List;

public interface StudentDao {

	Student saveNewStudent(Student student);

    Student updateStudentByStudentStatus(String studentNumber, StudentStatus newStatus);

    List<Student> getAllStudents();

    List<Student> saveStudents(List<Student> students);

	Student getStudentByStudentNumber(String studentNumber);
}
