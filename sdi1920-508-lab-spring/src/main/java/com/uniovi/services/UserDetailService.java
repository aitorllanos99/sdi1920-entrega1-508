package com.uniovi.services;

import org.springframework.security.core.userdetails.User;

public interface UserDetailService {
	User loadUserByUsername(String name);
}
