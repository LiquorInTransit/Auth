package com.gazorpazorp.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.gazorpazorp.service.LITUserDetailsService;

@SpringBootApplication
public class LITAuthApplication {
	
	@Profile("!test")
	@Configuration
	@EnableDiscoveryClient
	public static class EurekaClientConfiguration {		
	}
	
	
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
		
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		AuthenticationManager authenticationManager;
		
		@Autowired
		LITUserDetailsService userDetailsService;
		
		//This can stay the way it is
				@Override
				public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
					clients
						.inMemory()
							.withClient("LITClient")
							.authorizedGrantTypes("password", "refresh_token")
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
		
		@Override
		public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
			.tokenStore(tokenStore)
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService);
		}
		
		
	}
	
	
	@EnableResourceServer
	public static class ResourceServer {
		
	}
}
