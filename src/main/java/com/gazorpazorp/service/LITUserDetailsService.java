package com.gazorpazorp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gazorpazorp.dao.UserDao;
import com.gazorpazorp.model.User;


@Service("LITUserDetailsService")
public class LITUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userDao.findByUsername(name);
		if (user == null)
			throw new UsernameNotFoundException("Username not found");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
	}
	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return list;
	}

}
