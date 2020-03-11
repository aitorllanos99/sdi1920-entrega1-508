package com.uniovi.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationsService;
import com.uniovi.services.UsersService;

@Controller
public class PublicationsController {

	@Autowired
	private PublicationsService publicationsService;
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute Publication publication, Principal principal) {
		publication.setDate(new Date());
		publication.setUser(usersService.getUser(principal.getName()));
		publicationsService.addPublication(publication);
		return "redirect:/publication/list";
	}
	

	@RequestMapping(value = "/publication/add")
	public String getPublication() {
		return "publications/add";
	}

	
	
	@RequestMapping("/publication/list")
	public String getListado(Model model, Principal principal) {
		User user = usersService.getUser(principal.getName());
		List<Publication> publications = publicationsService.getPublicationsFromUser(user);

		model.addAttribute("publicationList", publications);

		return "publications/list";
	}
	
}
