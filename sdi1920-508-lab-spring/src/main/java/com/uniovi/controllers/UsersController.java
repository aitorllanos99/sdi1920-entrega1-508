package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private RolesService rolesService;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText, Principal principal) {
		User user = usersService.getUser(principal.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		users = usersService.searchByNameOrLastNameOrEmailOrRole(pageable, searchText, user);

		model.addAttribute("usersList", users.getContent());

		model.addAttribute("page", users);

		return "user/list";
	}

	@RequestMapping("/user/listFriends")
	public String getListadoAmgios(Model model, Pageable pageable, Principal principal) {
		User user = usersService.getUser(principal.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		users = usersService.getFriends(pageable, user.getEmail());

		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);

		return "user/list";
	}

	@RequestMapping("/user/friendPetitions")
	public String getListadoPeticiones(Model model, Pageable pageable, Principal principal) {
		User user = usersService.getUser(principal.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		users = usersService.getFriendPetition(pageable, user.getEmail());

		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);

		return "user/list";
	}

	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable String email) {
		model.addAttribute("user", usersService.getUser(email));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable String email) {
		usersService.deleteUser(email);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable String email) {
		User user = usersService.getUser(email);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable String email, @ModelAttribute User user) {
		User original = usersService.getUser(email);
		// modificar solo nombre y apellidos
		original.setName(user.getName());
		original.setLastName(user.getLastName());
		usersService.addUser(original);
		return "redirect:/user/details/" + email;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors())
			return "signup";
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

}