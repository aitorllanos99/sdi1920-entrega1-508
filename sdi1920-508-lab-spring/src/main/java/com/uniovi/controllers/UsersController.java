package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Friend;
import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.FriendsService;
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
	@Autowired
	private FriendRequestService friendRequestsService;
	@Autowired
	private FriendsService friendsService;

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText, Principal principal) {
		User user = usersService.getUser(principal.getName());
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		Page<FriendRequest> friendRequest = new PageImpl<FriendRequest>(new LinkedList<FriendRequest>());
		Page<Friend> friends = new PageImpl<Friend>(new LinkedList<Friend>());
		friendRequest = friendRequestsService.listByUser(pageable, user);
		friends = friendsService.listByUser(pageable, user);
		users = usersService.searchByNameOrLastNameOrEmailOrRole(pageable, searchText, user);

		isRequestable(friends, friendRequest, users);
		model.addAttribute("usersList", users.getContent());

		model.addAttribute("page", users);

		return "user/list";
	}

	@RequestMapping(value = "/user/friendPetition/{email}", method = RequestMethod.GET)
	public String setFriendRequest(Principal principal, @PathVariable String email, Model model, Pageable pageable) {
		User userTo = usersService.getUser(email);
		User userFrom = usersService.getUser(principal.getName());
		userTo.setRequestable(false);
		userFrom.setRequestable(false);
		friendRequestsService.addFriendRequest(userFrom, userTo);

		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		Page<FriendRequest> friendRequest = new PageImpl<FriendRequest>(new LinkedList<FriendRequest>());
		Page<Friend> friends = new PageImpl<Friend>(new LinkedList<Friend>());
		friendRequest = friendRequestsService.listByUser(pageable, userFrom);
		friends = friendsService.listByUser(pageable, userFrom);

		users = usersService.searchByNameOrLastNameOrEmailOrRole(pageable, null, userFrom);

		isRequestable(friends, friendRequest, users);
		model.addAttribute("usersList", users.getContent());

		model.addAttribute("page", users);
		return "redirect:/user/list";
	}

	private void isRequestable(Page<Friend> friends, Page<FriendRequest> friendRequest, Page<User> users) {
		for (FriendRequest fr : friendRequest) {
			for (User u : users) {
				for (Friend f : friends) {
					if (fr.getTo().getEmail().equals(u.getEmail()) || fr.getFrom().getEmail().equals(u.getEmail())
							|| f.getFriend().getEmail().equals(u.getEmail()))
						u.setRequestable(false);
					else
						u.setRequestable(true);
				}
			}
		}
	}

	@RequestMapping("/user/listFriends")
	public String getListadoAmgios(Model model, Pageable pageable, Principal principal) {
		User user = usersService.getUser(principal.getName());
		List<User> users = new LinkedList<User>();
		friendsService.listByUser(pageable, user).getContent().forEach(c -> users.add(c.getFriend()));

		Page<User> usersP = new PageImpl<User>(users);
		model.addAttribute("usersList", usersP.getContent());
		model.addAttribute("page", usersP);

		return "user/listFriends";
	}

	@RequestMapping("/user/listPetitions")
	public String getListadoPeticiones(Model model, Pageable pageable, Principal principal) {
		User user = usersService.getUser(principal.getName());
		List<User> users = new LinkedList<User>();
		friendRequestsService.listByUser(pageable, user).getContent().forEach(c -> users.add(c.getFrom()));

		Page<User> usersP = new PageImpl<User>(users);
		model.addAttribute("usersList", usersP.getContent());
		model.addAttribute("page", usersP);

		return "user/listFriendPetitions";
	}

	@RequestMapping("/user/listPetitions/reject/{email}")
	public String rejectPetition(Principal principal, @PathVariable String email) {
		User userFrom = usersService.getUser(email);
		User userTo = usersService.getUser(principal.getName());
		userTo.setRequestable(true);
		userFrom.setRequestable(true);
		friendRequestsService.deleteFriendRequest(userFrom, userTo);
		return "redirect:/user/listPetitions";
	}

	@RequestMapping("/user/listPetitions/accept/{email}")
	public String acceptPetition(Principal principal, @PathVariable String email) {
		User userFriend = usersService.getUser(email);
		User user = usersService.getUser(principal.getName());
		user.setRequestable(false);
		userFriend.setRequestable(false);
		friendsService.addFriend(userFriend, user);
		friendRequestsService.deleteFriendRequest(userFriend, user);
		return "redirect:/user/listPetitions";
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