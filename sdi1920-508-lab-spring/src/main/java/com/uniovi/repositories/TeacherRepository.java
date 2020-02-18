package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

}
