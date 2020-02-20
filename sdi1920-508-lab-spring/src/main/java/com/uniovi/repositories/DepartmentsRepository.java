package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Department;

public interface DepartmentsRepository extends CrudRepository<Department, Long> {

}
