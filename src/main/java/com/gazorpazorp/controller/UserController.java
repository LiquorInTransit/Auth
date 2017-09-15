package com.gazorpazorp.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.User;
import com.gazorpazorp.service.UserService;

@RestController
@RequestMapping("/uaa")
@EnableResourceServer
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
	//Current user mapping (for fetches and business logic)
	@GetMapping("/me")
	public ResponseEntity me (Principal principal){
		User user = null;
		if (principal != null) {
			user = userService.getUserById(Long.parseLong(principal.getName()));
		}
		return Optional.ofNullable(user)
				.map(a -> new ResponseEntity<User>(a, HttpStatus.OK))
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}
	
	@PreAuthorize("#oauth2.hasScope('system')")
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{
		return Optional.ofNullable(userService.create(user))
				.map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
				.orElseThrow(() -> new Exception("An error occured creating user"));
	}
	
	@PreAuthorize("#oauth2.hasScope('system')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteUserByUserId(@PathVariable Long id) {
		userService.deleteById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PreAuthorize("#oauth2.hasScope('system')")
	@GetMapping("/users/{id}")
	public ResponseEntity getUserById(@PathVariable Long id) throws Exception {
		return Optional.ofNullable(userService.getUserById(id))
				.map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
				.orElseThrow(() -> new Exception("User not found"));
	}
}
