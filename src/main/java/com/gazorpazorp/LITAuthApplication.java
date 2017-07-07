package com.gazorpazorp;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.dao.UserDao;
import com.gazorpazorp.service.LITUserDetailsService;

//@EnableDiscoveryClient
@SpringBootApplication
public class LITAuthApplication {
	@Autowired
	LITUserDetailsService userDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(LITAuthApplication.class, args);
	}
	@Autowired
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuthConfig extends AuthorizationServerConfigurerAdapter {
		@Autowired
		AuthenticationManager authenticationManager;
		@Override
		public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
					.withClient("LITClient")
					.authorizedGrantTypes("password", "client_credentials", "refresh_token")
					.authorities("ADMIN")
					.scopes("read", "write")
					.secret("LITSecret")
					.accessTokenValiditySeconds(82000)
				.and()
					.withClient("LITSystem")
					.authorizedGrantTypes("client_credentials")
					.authorities("ADMIN")
					.scopes("system")
					.secret("LITSystem")
					.accessTokenValiditySeconds(180);
		}		
	}
	
	@RestController
	@EnableResourceServer
	public static class UserController {
		@RequestMapping("/user")
		public Principal user (Principal user){
			return user;
		}
	}
}
