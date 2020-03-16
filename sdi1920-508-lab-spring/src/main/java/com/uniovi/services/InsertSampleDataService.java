package com.uniovi.services;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private FriendRequestService friendRequestsService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private PublicationsService publicationsService;

	@PostConstruct
	public void init() {
		User user1 = new User("macmiller@gmail.com", "Malcom", "McCormick");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[0]);
		User admin2 = new User("admin2@email.com", "The notorious", "Biggie");
		admin2.setPassword("123456");
		admin2.setRole(rolesService.getRoles()[1]);

		User user2 = new User("blake@gmail.com", "Blake", "Musica");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("delaossa@gmail.com", "Delaossa", "Malaga");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);

		User user4 = new User("ayax@gmail.com", "Ayax", "Albayzin");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);

		User user5 = new User("postmalone@gmail.com", "Austin", "Post");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);

		User userpag2 = new User("travisscott@gmail.com", "Travis", "Scott");
		userpag2.setPassword("123456");
		userpag2.setRole(rolesService.getRoles()[0]);

		User userpag22 = new User("trippiered@gmail.com", "Trippie", "Redd");
		userpag22.setPassword("123456");
		userpag22.setRole(rolesService.getRoles()[0]);

		User user6 = new User("admin@email.com", "Tupac", "Shakur");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[1]);

		Publication p1 = new Publication("Blue Slide Park", "Up All Night", new Date());
		p1.setUser(user1);

		Publication p2 = new Publication("Swimming", "Perfecto", new Date());
		p2.setUser(user1);

		Publication p3 = new Publication("Beerbongs and Bentleys", "Paranoid", new Date());
		p3.setUser(user5);

		Publication p4 = new Publication("Talisman", "Ideales", new Date());
		p4.setUser(user2);

		Publication p5 = new Publication("Astroworld", "Yosemite", new Date());
		p5.setUser(userpag2);

		usersService.addUser(user1);
		usersService.addUser(admin2);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(userpag2);
		usersService.addUser(userpag22);

		publicationsService.addPublication(p1);
		publicationsService.addPublication(p2);
		publicationsService.addPublication(p3);
		publicationsService.addPublication(p4);
		publicationsService.addPublication(p5);

		friendRequestsService.addFriendRequest(user2, user1);
		friendsService.addFriend(userpag2, user1);
	}
}