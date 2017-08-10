package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER")
public class User {
	//Sign in details
	private Long id;
	private String username;
	private String password;

	//Revocation details
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	
	public User() {}
	
	@Id
	@Column(name = "id", length = 30)
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "username", length = 30)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Column(name = "password", length = 60)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	@Column(name = "non_expired")
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@JsonIgnore
	@Column(name = "non_locked")
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	
}
