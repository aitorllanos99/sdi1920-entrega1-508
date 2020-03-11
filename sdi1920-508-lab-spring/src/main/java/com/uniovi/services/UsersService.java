package com.uniovi.services;

import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable, User user) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		usersRepository.findAll(pageable);
		return users;
	}

	public User getUser(String email) {
		return usersRepository.findByEmail(email);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public void deleteUser(String email) {
		usersRepository.deleteById(email);
	}
	
	/*
	 * public Page<User> getFriends(Pageable pageable, String email){ return
	 * usersRepository.findFriends(pageable, email); }
	 * 
	 * 
	 * public Page<User> getFriendPetition(Pageable pageable, String email){ return
	 * usersRepository.findFriendPetitions(pageable, email); }
	 */

	public Page<User> searchByNameOrLastNameOrEmailOrRole(Pageable pageable, String searchText, User user) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		if (searchText.equals("%null%") || searchText.isEmpty()) {
			if (user.getRole() == "ROLE_ADMIN") {
				users = usersRepository.findAllAutenticated(pageable, user.getEmail());
			}else {
				users = usersRepository.findAllAutenticatedNotAdmin(pageable, user.getEmail());
			}
		} else {
			if (user.getRole() == "ROLE_ADMIN") {
				users = usersRepository.findByNameOrLastNameOrEmail(pageable, searchText, user.getEmail());
			} else {
				users = usersRepository.findByNameOrLastNameOrEmailAndAutenticated(pageable, searchText,
						user.getEmail());
			}
		}
		return users;
	}
	
//	public void sendFriendPetition(String emailFrom, String emailTo){
//		usersRepository.sendPetition(emailFrom, emailTo);
//	}

}