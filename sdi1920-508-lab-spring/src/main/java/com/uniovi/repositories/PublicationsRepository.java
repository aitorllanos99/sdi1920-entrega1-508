package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;

public interface PublicationsRepository extends CrudRepository<Publication, Long> {

	@Query("SELECT r From Publication r WHERE (LOWER(r.user.email)=LOWER(?1))")
	List<Publication> findAllByUser(String email);
}
