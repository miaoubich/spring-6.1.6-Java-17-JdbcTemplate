package com.miaoubich.service.dao;

import java.util.List;

import com.miaoubich.model.Student;

public interface StudentDao {

	Student saveStudent(Student student);

	void saveStudents(List<Student> students);
}
