package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class FriendRequest {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private User from;

	@ManyToOne
	private User to;

	public FriendRequest() {
		// TODO Auto-generated constructor stub
	}

	public FriendRequest(User from, User to) {
		this.from = from;
		this.to = to;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}
	
	
}
