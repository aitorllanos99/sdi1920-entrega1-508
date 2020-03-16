package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {

	@Autowired
	private PublicationsRepository publicationsrepository;

	public void addPublication(Publication publication) {
		publicationsrepository.save(publication);
	}

	public List<Publication> getPublicationsFromUser(User user) {
		return publicationsrepository.findAllByUser(user.getEmail());
	}
}
