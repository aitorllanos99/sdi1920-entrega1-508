package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Teacher;
import com.uniovi.repositories.TeacherRepository;


@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
	public Teacher findByDni(String dni) {
		return teacherRepository.findByDni(dni);
	}
	
	public List<Teacher> getTeachers(){
		List<Teacher> teachers = new ArrayList<Teacher>();
		teacherRepository.findAll().forEach(teachers::add);
		return teachers;
	}
	public Teacher getTeacher(Long id) {
		return teacherRepository.findById(id).get();
	}
	
	
	public void addTeacher(Teacher teacher) {
		teacherRepository.save(teacher);
	}
	
	public void deleteTeacher(Long id) {
		teacherRepository.deleteById(id);
	}
	
}
