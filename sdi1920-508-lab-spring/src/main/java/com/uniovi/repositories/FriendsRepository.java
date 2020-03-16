package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friend;

public interface FriendsRepository extends CrudRepository<Friend, Long> {
	
	@Query("Select r FROM Friend r WHERE(LOWER(r.user.email)=LOWER(?1))")
	Page<Friend> findFriendsByUser(Pageable pageable, String email);
}
