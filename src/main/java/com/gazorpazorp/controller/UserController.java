package com.gazorpazorp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.UserPrincipal;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@GetMapping("/me")
	UserPrincipal profile (@AuthenticationPrincipal UserPrincipal user) {
		System.out.println("me");
		System.out.println(user);
		return user;
	}
	
//	@Data
//	@AllArgsConstructor
//	@NoArgsConstructor
//	class PrincipalDetails {
//		private String userId;
//		private String customerId;
//	}
}
