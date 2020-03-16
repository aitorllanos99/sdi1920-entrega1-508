package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Friend {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private User friend;

	
	
	public Friend() {
		// TODO Auto-generated constructor stub
	}
	
	public Friend(User friend, User user) {
		this.friend = friend;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
	
	

}
