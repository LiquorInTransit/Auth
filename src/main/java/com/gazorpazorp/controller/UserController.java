package com.gazorpazorp.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.User;
import com.gazorpazorp.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//UserInfoUri
	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
	//Current user mapping (for fetches and business logic)
	@GetMapping("/me")
	public ResponseEntity me (Principal principal){
		User user = null;
		if (principal != null) {
			user = userService.getUserByUsername(principal.getName());
		}
		
		return Optional.ofNullable(user)
				.map(a -> new ResponseEntity<User>(a, HttpStatus.OK))
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}
}
