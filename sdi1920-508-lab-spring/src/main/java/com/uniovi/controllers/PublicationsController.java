package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddPublicationValidator;

@Controller
public class PublicationsController {

	@Autowired
	private PublicationsService publicationsService;
	@Autowired
	private AddPublicationValidator addPublicationValidator;
	@Autowired
	private UsersService usersService;
	

	@RequestMapping(value = "/publication/add", method = RequestMethod.GET)
	public String getPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publications/add";
	}
	
	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String setPublication(@Validated Publication publication, BindingResult result, Principal principal) {
		addPublicationValidator.validate(publication, result);
		if (result.hasErrors())
			return "publications/add";
		publication.setDate(new Date());
		publication.setUser(usersService.getUser(principal.getName()));
		publicationsService.addPublication(publication);
		return "redirect:/publication/list";
	}

	
	
	@RequestMapping("/publication/list")
	public String getListado(Model model, Principal principal) {
		User user = usersService.getUser(principal.getName());
		List<Publication> publications = publicationsService.getPublicationsFromUser(user);
		model.addAttribute("user", usersService.getUser(principal.getName()));
		model.addAttribute("publicationsList", publications);

		return "publications/list";
	}
	
	@RequestMapping(value = "/publication/list/{email}", method = RequestMethod.GET)
	public String getListado(Model model,@PathVariable String email) {
		User user = usersService.getUser(email);
		List<Publication> publications = publicationsService.getPublicationsFromUser(user);

		model.addAttribute("publicationsList", publications);

		return "publications/list";
	}
}
