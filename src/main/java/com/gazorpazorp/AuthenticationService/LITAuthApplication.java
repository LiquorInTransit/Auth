package com.gazorpazorp.AuthenticationService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import com.gazorpazorp.service.LITUserDetailsService;
import com.gazorpazorp.service.UserService;

@SpringBootApplication(scanBasePackages="com.gazorpazorp")
@EnableJpaRepositories("com.gazorpazorp.repository")
@EntityScan(basePackages="com.gazorpazorp")
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

		@Bean
		protected JwtAccessTokenConverter accessTokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "8167255".toCharArray());
			converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
			return converter;
		}
//		@Autowired
//		TokenEnhancer customTokenEnhancer;
		@Bean
		public TokenStore tokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}
		
		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        defaultTokenServices.setSupportRefreshToken(true);
	        return defaultTokenServices;
		}

		@Autowired
		AuthenticationManager authenticationManager;

		//		@Autowired
		//		LITUserDetailsService userDetailsService;

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
			TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(/*customTokenEnhancer, */accessTokenConverter()));
			
			endpoints
			.tokenStore(tokenStore())
			//.accessTokenConverter(accessTokenConverter())
			.tokenEnhancer(tokenEnhancerChain)
			.exceptionTranslator(loggingExceptionTranslator())
			.authenticationManager(authenticationManager);
		}		
		
		@Bean
	    public WebResponseExceptionTranslator loggingExceptionTranslator() {
	        return new DefaultWebResponseExceptionTranslator() {
	            @Override
	            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
	                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
	                e.printStackTrace();

	                // Carry on handling the exception
	                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
	                HttpHeaders headers = new HttpHeaders();
	                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
	                OAuth2Exception excBody = responseEntity.getBody();
	                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
	            }
	        };
	    }
	}
	
	
	
	
//	@Component
//	@Qualifier("customTokenEnhancer")
//	public static class CustomTokenEnhancer implements TokenEnhancer {
//		@Autowired
//		UserService userService;
//		@Override
//		public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//			Map<String, Object> additionalInfo = new HashMap<>();
//			additionalInfo.put("user_id", userService.getUserByUsername(authentication.getUserAuthentication().getName()).getId());
//			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
//	        return accessToken;
//		}
//	}



	@EnableResourceServer
	public static class ResourceServer {

	}
}
