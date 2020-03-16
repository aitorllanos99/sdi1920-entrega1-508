package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequest;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestsRepository;

@Service
public class FriendRequestService {
	@Autowired
	private FriendRequestsRepository friendRequestsRepository;

	public void addFriendRequest(User from, User to) {
		FriendRequest friendRequest = new FriendRequest(from, to);
		friendRequestsRepository.save(friendRequest);
	}

	public void deleteFriendRequest(Long id) {
		friendRequestsRepository.deleteById(id);
	}

	public Page<FriendRequest> listByUser(Pageable pageable, User user) {
		Page<FriendRequest> friendRequest = new PageImpl<FriendRequest>(new LinkedList<FriendRequest>());
		friendRequest = friendRequestsRepository.findFriendPetitionsByUser(pageable, user.getEmail());
		return friendRequest;
	}
	
	public void deleteFriendRequest(User from, User to) {
		deleteFriendRequest(friendRequestsRepository.findByUsers(from.getEmail(),to.getEmail()).getId());
	}
	
	public List<FriendRequest> findAll() {
		List<FriendRequest> fr = new ArrayList<FriendRequest>();
		 friendRequestsRepository.findAll().forEach(fr::add);
		 return fr;
	}

}
