package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, String> {

	User findByEmail(String email);

	Page<User> findAll(Pageable pageable);

	@Query("SELECT r FROM User r WHERE ((LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1)) AND r.email<>LOWER(?2))")
	Page<User> findByNameOrLastNameOrEmail(Pageable pageable, String searchText, String email);

	@Query("SELECT r FROM User r WHERE ((LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1)) AND r.role <>('ROLE_ADMIN') AND r.email<>LOWER(?2))")
	Page<User> findByNameOrLastNameOrEmailAndAutenticated(Pageable pageable, String searchText, String email);

	@Modifying
	@Transactional
	@Query("UPDATE User SET friendPetition = ?1 WHERE id = ?2 AND id=?3")
	void updateFriendPetition(Boolean friendPetition, Long id, Long id2);
}
