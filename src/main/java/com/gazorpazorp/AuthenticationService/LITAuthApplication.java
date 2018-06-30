package com.gazorpazorp.AuthenticationService;

import javax.annotation.PostConstruct;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(scanBasePackages="com.gazorpazorp")
@EnableJpaRepositories("com.gazorpazorp")
@EntityScan(basePackages="com.gazorpazorp")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class LITAuthApplication {

//	@Autowired
//	LITUserDetailsService userDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(LITAuthApplication.class, args);
	}
	
	@PostConstruct
	public void getDbManager(){
	   DatabaseManagerSwing.main(
		new String[] { "--url", "jdbc:hsqldb:mem:test://localhost/test?characterEncoding=UTF-8", "--user", "SA", "--password", ""});
	}
	
//	@Autowired
//	public void init(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Component
//	public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
//		@Override
//		public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
//			OAuth2Authentication authentication = super.extractAuthentication(map);
//			authentication.setDetails(map);
//			return authentication;
//		}
//	}
//
//	@Configuration
//	@EnableAuthorizationServer
//	protected static class OAuthConfig extends AuthorizationServerConfigurerAdapter {
//		
//		@Autowired
//		CustomAccessTokenConverter customAccessTokenConverter;
//		
//		@Bean
//		protected JwtAccessTokenConverter accessTokenConverter() {
//			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//			KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "8167255".toCharArray());
//			converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
//			converter.setAccessTokenConverter(customAccessTokenConverter);
//			return converter;
//		}
//		
//		
//		
//		
//		
//		
//		
//		
//		
////		@Autowired
////		TokenEnhancer customTokenEnhancer;
//		@Bean
//		public TokenStore tokenStore() {
//			return new JwtTokenStore(accessTokenConverter());
//		}
//		
//		@Bean
//		@Primary
//		public DefaultTokenServices tokenServices() {
//			DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//	        defaultTokenServices.setTokenStore(tokenStore());
//	        defaultTokenServices.setSupportRefreshToken(true);
//	        return defaultTokenServices;
//		}
//
//		@Autowired
//		AuthenticationManager authenticationManager;
//
//
//		//This can stay the way it is
//		/* (non-Javadoc)
//		 * scope definitions:
//		 * cutomer: only for customers. doubles as 'customer profile'
//		 * driver: only for drivers. doubles as 'driver profile'
//		 * orders: allowed to view orders (technically customer only)
//		 * signup: allowed to make signup requests
//		 * system: only allowed for the system. no user/clients allowed
//		*/
//		
//		@Override
//		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//			clients
//			.inMemory()
//			.withClient("LITCustomerClient")
//			.authorizedGrantTypes("password", "refresh_token")
//			.authorities("ADMIN")
//			.scopes("customer", "orders")
//			.secret("LITSecret")
//			.accessTokenValiditySeconds(82000000)
//			.and()
//			.withClient("LITDriverClient")
//			.authorizedGrantTypes("password", "refresh_token")
//			.authorities("ADMIN")
//			.scopes("driver")
//			.secret("LITSecret")
//			.accessTokenValiditySeconds(82000000)
//			.and()
//			.withClient("LITSystem")
//			.authorizedGrantTypes("client_credentials")
//			.authorities("ADMIN")
//			.scopes("system")
//			.secret("LITSystem")
//			.accessTokenValiditySeconds(180)
//			.and()
//			.withClient("LITWebClient")
//			.authorizedGrantTypes("password", "refresh_token")
//			.authorities("ADMIN")
//			.scopes("read", "write")
//			.secret("LITSecret")
//			.accessTokenValiditySeconds(82000)			
//			.and()
//			.withClient("LITSignUpClient")
//			.authorizedGrantTypes("client_credentials")
//			.authorities("ADMIN")
//			.scopes(/*"system", */"signup")
//			.secret("LITSystem")
//			.accessTokenValiditySeconds(1000);
//		}		
//
//		@Override
//		public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//			TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
//			tokenEnhancerChain.setTokenEnhancers(Arrays.asList(/*customTokenEnhancer, */tokenEnhancer(), accessTokenConverter()));
//			
//			endpoints
//			.pathMapping("/oauth/token", "/uaa/oauth/token")
//			.tokenStore(tokenStore())
//			//.accessTokenConverter(accessTokenConverter())
//			.tokenEnhancer(tokenEnhancerChain)
//			.exceptionTranslator(loggingExceptionTranslator())
//			.authenticationManager(authenticationManager);
//		}	
//		
//		@Bean
//		public TokenEnhancer tokenEnhancer() { 
//			return new LITTokenEnhancer();
//		}
//		
//		@Bean
//	    public WebResponseExceptionTranslator loggingExceptionTranslator() {
//	        return new DefaultWebResponseExceptionTranslator() {
//	            @Override
//	            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
//	                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
//	                e.printStackTrace();
//
//	                // Carry on handling the exception
//	                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
//	                HttpHeaders headers = new HttpHeaders();
//	                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
//	                OAuth2Exception excBody = responseEntity.getBody();
//	                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
//	            }
//	        };
//	    }
//		
//		public class LITTokenEnhancer implements TokenEnhancer {
//			@Autowired
//			UserRepository userRepository;
//			@Override
//			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//				Map<String, Object> additionalInfo = new HashMap<>();
//				User user = userRepository.findByEmail(authentication.getName());
//				additionalInfo.put("userId", user.getId());
//				Set<String> scopes =  accessToken.getScope();
//				if (scopes.contains("customer")) {
//					additionalInfo.put("customerId", user.getId());
//				} else if (scopes.contains("driver")) {
//					additionalInfo.put("driverId", user.getId());
//				}
//				((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
//				return accessToken;
//			}
//		}
//	}
	
	
}
