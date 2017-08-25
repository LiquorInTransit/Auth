package com.gazorpazorp.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gazorpazorp.model.User;
import com.gazorpazorp.repository.UserRepository;



@Component
public class ApplicationLoader implements CommandLineRunner{	
	public void run(String... args) throws Exception {
		
	}
}
