package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequest;

public interface FriendRequestsRepository extends CrudRepository<FriendRequest, Long> {
	
	@Query("Select r FROM FriendRequest r WHERE(LOWER(r.to.email)=LOWER(?1))")
	Page<FriendRequest> findFriendPetitionsByUser(Pageable pageable, String email);
	
	@Query("Select r FROM FriendRequest r WHERE(LOWER(r.from.email)=LOWER(?1) AND LOWER(r.to.email)=LOWER(?2))")
	FriendRequest findByUsers(String from, String to);
}
