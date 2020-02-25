package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Teacher;
import com.uniovi.services.TeacherService;
import com.uniovi.validators.AddTeacherValidator;

@Controller
public class TeacherController {
	@Autowired // Inyectar el servicio
	private TeacherService teacherService;

	@Autowired
	private AddTeacherValidator addTeacherValidator;
	@RequestMapping(value = "/teacher/add", method = RequestMethod.GET)
	public String setTeacher(Model model) {
		model.addAttribute("teacher",new Teacher());
		return "/teacher/add";
	}
	
	
	@RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
	public String setTeacher(@Validated Teacher teacher, BindingResult result) {
		addTeacherValidator.validate(teacher, result);
		if(result.hasErrors())
			return "teacher/add";
		teacherService.addTeacher(teacher);
		return "redirect:/teacher/list";
	}
	
	@RequestMapping("/teacher/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("teacher", teacherService.getTeacher(id));
		return "teacher/details";
	}
	
	@RequestMapping("/teacher/delete/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		teacherService.deleteTeacher(id);
		return "redirect:/teacher/list";
	}
	
	@RequestMapping(value = "/teacher/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Teacher teacher) {
		teacher.setId(id);
		teacherService.addTeacher(teacher);
		return "redirect:/teacher/details/" + id;
	}
}
