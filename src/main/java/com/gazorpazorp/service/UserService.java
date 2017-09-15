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
	private UserRepository userRepo;
	
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	public User getUserById(Long id) {
		return userRepo.findById(id).get();
	}
	
	
	public User create(User user) {
		User existing = userRepo.findByUsername(user.getUsername());
		Assert.isNull(existing, "User already exists: " + user.getUsername());
		
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setRoles("CUSTOMER");
		
		user =  userRepo.save(user);
		return user;
	}
	
	public void deleteById (Long id) {
		userRepo.deleteById(id);
	}
}
