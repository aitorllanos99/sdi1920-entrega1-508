package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, String> {

	User findByEmail(String email);

	Page<User> findAll(Pageable pageable);

	@Query("SELECT r FROM User r WHERE ((LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1)) AND LOWER(r.email)<>LOWER(?2))")
	Page<User> findByNameOrLastNameOrEmail(Pageable pageable, String searchText, String email);

	@Query("SELECT r FROM User r WHERE (LOWER(r.email)<>LOWER(?1))")
	Page<User> findAllAutenticated(Pageable pageable, String email);

	@Query("SELECT r FROM User r WHERE (LOWER(r.email)<>LOWER(?1) AND LOWER(r.role)<>LOWER('ROLE_ADMIN') )")
	Page<User> findAllAutenticatedNotAdmin(Pageable pageable, String email);

	@Query("SELECT r FROM User r WHERE ((LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1)) AND LOWER(r.role)<>LOWER('ROLE_ADMIN') AND LOWER(r.email)<>LOWER(?2))")
	Page<User> findByNameOrLastNameOrEmailAndAutenticated(Pageable pageable, String searchText, String email);

	
}
