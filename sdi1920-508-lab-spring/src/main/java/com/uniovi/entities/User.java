package com.uniovi.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {
	@Id
	@Column(nullable = false)
	private String email;

	private String name;
	private String lastName;
	private String role;
	private String password;

	
	private boolean requestable = true;

	@OneToMany(mappedBy = "from")
	private Set<FriendRequest> sentRequests;
	@OneToMany(mappedBy = "to")
	private Set<FriendRequest> incomingRequests;

	@OneToMany(mappedBy = "friend")
	private Set<Friend> friends;

	@OneToMany(mappedBy = "user")
	private Set<Publication> publications;

	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;

	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", lastName=" + lastName + ", role=" + role + "]";
	}

	public boolean isRequestable() {
		return requestable;
	}

	public void setRequestable(boolean requestable) {
		this.requestable = requestable;
	}

	public Set<FriendRequest> getSentRequests() {
		return sentRequests;
	}

	public void setSentRequests(Set<FriendRequest> sentRequests) {
		this.sentRequests = sentRequests;
	}

	public Set<FriendRequest> getIncomingRequests() {
		return incomingRequests;
	}

	public void setIncomingRequests(Set<FriendRequest> incomingRequests) {
		this.incomingRequests = incomingRequests;
	}

	public Set<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}

}