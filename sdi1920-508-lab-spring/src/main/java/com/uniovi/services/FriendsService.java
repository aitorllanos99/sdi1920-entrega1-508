package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friend;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendsRepository;

@Service
public class FriendsService {

	@Autowired
	private FriendsRepository friendsRepository;
	
	public void addFriend(User friend, User inSession) {
		Friend friendEntity = new Friend(friend,inSession);
		friendsRepository.save(friendEntity);
		Friend friendEntity2 = new Friend(inSession,friend);
		friendsRepository.save(friendEntity2);
	}
	
	public void deleteFriend(Long id) {
		friendsRepository.deleteById(id);
	}
	
	public Page<Friend> listByUser(Pageable pageable, User user) {
		return friendsRepository.findFriendsByUser(pageable, user.getEmail());
	}
}
