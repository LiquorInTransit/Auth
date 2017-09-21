package com.gazorpazorp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.gazorpazorp.model.User;
import com.gazorpazorp.repository.UserRepository;

@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepo;
	
	public User getUserById(Long id) {
		return userRepo.findById(id).get();
	}
	public User updateUser(Long id, String email, String phone, String password) {
			User user = getUserById(id);
			if (email != null) user.setEmail(email);
			//if (phone != null) user.setPhoneNumber(phone);TODO: add a phone number option. verify correct phone format and is not taken.
			if (password != null) user.setPassword(new BCryptPasswordEncoder().encode(password)); //consider only accepting a pre-encoded password
			return userRepo.save(user);
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
}
