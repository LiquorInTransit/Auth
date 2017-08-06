package com.gazorpazorp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.model.User;
import com.gazorpazorp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public User getUserById(String id) {
		return userRepository.findById(Long.parseLong(id)).get();
	}
}
