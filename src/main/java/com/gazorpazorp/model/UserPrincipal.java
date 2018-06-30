package com.gazorpazorp.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

	private Long userId;
	private Long customerId;
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	
	public static UserPrincipal create (User user) {
		 List<GrantedAuthority> authorities = Arrays.asList(user.getRoles().split(",")).stream().map(role ->
		 new SimpleGrantedAuthority("ROLE_"+role)
 ).collect(Collectors.toList());
		 
		 return new UserPrincipal (user.getId(), user.getId(), user.getEmail(), user.getPassword(), authorities, user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked());
	}
	
	public UserPrincipal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getUsername() {
		return email;
	}

	
}
