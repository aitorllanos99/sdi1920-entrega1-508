package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.entities.Department;
import com.uniovi.repositories.DepartmentsRepository;

public class DepartmentService {
	@Autowired
	private DepartmentsRepository departmentRepository;

	@PostConstruct
	public void init() {
	}

	public List<Department> getDepartment() {
		List<Department> departments = new ArrayList<Department>();
		departmentRepository.findAll().forEach(departments::add);
		return departments;
	}

	public Department getDepartment(Long id) {
		return departmentRepository.findById(id).get();
	}

	public void addDepartment(Department department) {
		departmentRepository.save(department);
	}

	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
	}
}
