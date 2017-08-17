package com.gazorpazorp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.gazorpazorp.model.User;
import com.gazorpazorp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}
	
	
	public User create(User user) {
		User existing = userRepository.findByUsername(user.getUsername());
		Assert.isNull(existing, "User already exists: " + user.getUsername());
		
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		
		user =  userRepository.save(user);
		System.out.println(user);
		return user;
	}
}
