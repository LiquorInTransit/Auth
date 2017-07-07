package com.gazorpazorp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gazorpazorp.dao.UserDao;
import com.gazorpazorp.model.User;



@Component
public class ApplicationLoader implements CommandLineRunner{

	@Autowired
	Environment environment;
	@Autowired
	UserDao userDao;
	
	public void run(String... args) throws Exception {
		
		System.out.println(userDao.findAll());
	}
	
	
	
}
