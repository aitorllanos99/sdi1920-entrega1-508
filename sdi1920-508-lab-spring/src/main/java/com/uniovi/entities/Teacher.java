package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Teacher {

	@Id
	@GeneratedValue
	private Long id;
	private String dni;
	private String nombre;
	private String apellido;
	private String categoria;
	@ManyToOne
	@JoinColumn(name = "department_id")private Department department;
	
	public Teacher(Long id, String dni, String nombre, String apellido, String categoria, Department department) {
		super();
		this.id  = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.categoria = categoria;
		this.department = department;
	}
	
	public Teacher() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}
