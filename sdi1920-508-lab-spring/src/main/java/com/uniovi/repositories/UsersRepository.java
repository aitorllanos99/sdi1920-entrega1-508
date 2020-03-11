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

	@Query("SELECT r FROM User r WHERE ((LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1)) AND LOWER(r.email)<>LOWER(?2))")
	Page<User> findByNameOrLastNameOrEmail(Pageable pageable, String searchText, String email);

	@Query("SELECT r FROM User r WHERE (LOWER(r.email)<>LOWER(?1))")
	Page<User> findAllAutenticated(Pageable pageable, String email);
	
	@Query("SELECT r FROM User r WHERE (LOWER(r.email)<>LOWER(?1) AND LOWER(r.role)<>LOWER('ROLE_ADMIN') )")
	Page<User> findAllAutenticatedNotAdmin(Pageable pageable, String email);

	@Query("SELECT r FROM User r WHERE ((LOWER(r.lastName) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1) OR LOWER(r.email) LIKE LOWER(?1)) AND LOWER(r.role)<>LOWER('ROLE_ADMIN') AND LOWER(r.email)<>LOWER(?2))")
	Page<User> findByNameOrLastNameOrEmailAndAutenticated(Pageable pageable, String searchText, String email);

	/*
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query("UPDATE User SET FriendPetitions = ?1 WHERE id = ?2 AND id=?3") void
	 * updateFriendPetition(Boolean friendPetition, Long id, Long id2);
	 * 
	 * @Query("Select r FROM Friends r WHERE(LOWER(r.personId)=LOWER(?1))")
	 * Page<User> findFriends(Pageable pageable, String email);
	 * 
	 * @Query("UPDATE FriendPetitions SET PERSONID = ?1 AND FRIENDID) VALUES(?1,?2)"
	 * ) void sendPetition(String emailFrom, String emailTo);
	 * 
	 * @Query("UPDATE Friends(PERSONID,FRIENDID) VALUES(?1,?2)") void
	 * setFriend(String emailFrom, String emailTo);
	 * 
	 * @Query("Select r FROM FriendPetitions r WHERE(LOWER(r.personId)=LOWER(?1))")
	 * Page<User> findFriendPetitions(Pageable pageable, String email);
	 */
}
