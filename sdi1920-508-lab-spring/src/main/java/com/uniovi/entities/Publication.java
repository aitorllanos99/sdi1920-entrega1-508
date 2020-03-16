package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.type.ImageType;

@Entity
public class Publication {

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String content;
	private Date date;

	private ImageType photo;
	
	@ManyToOne
	private User user;

	public Publication() {
		// TODO Auto-generated constructor stub
	}
	public Publication(String title, String content, Date date) {
		this.title = title;
		this.content = content;
		this.date = date;
	}
	public Publication(String title, String content, Date date, ImageType image) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.photo = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public ImageType getPhoto() {
		return photo;
	}

	public void setPhoto(ImageType photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Publication [id=" + id + ", title=" + title + ", content=" + content + ", date=" + date + ", user="
				+ user + "]";
	}

}
