package com.estudo.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.estudo.api.config.property.PokedexApiProperty;
import com.estudo.api.config.token.CustomTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PokedexApiProperty property;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("angular")
				.secret("$2a$10$LEDtu9R5oMJFIhxyW78sT.AZnBVvnLHC0zKJsA.FdiTdIfdMeSV.O") //@ngul@ar0
				.scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(property.getToken().getTokenDesktop()) // 1800 / 60 = 30 minutos
				.refreshTokenValiditySeconds(property.getToken().getTokenRefreshDesktop()) // 24 horas
			.and()
				.withClient("mobile")
				.secret("$2a$10$zAIki/O96hzDjJbnDx9B7.1R5MxOPLVmOQiLQnXhAA/kwD.NBaBWy") //m0b1l30
				.scopes("read")
				.authorizedGrantTypes("password")
				.accessTokenValiditySeconds(property.getToken().getTokenMobile()); // 3600 / 60 = 60 minutos
				//.refreshTokenValiditySeconds(3600 * 24); // 24 horas
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints
		.tokenStore(tokenStore())
		.tokenEnhancer(tokenEnhancerChain)
		.reuseRefreshTokens(false)
		.userDetailsService(this.userDetailsService)
		.authenticationManager(this.authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey(property.getToken().getSegredo());
		return accessTokenConverter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}

/*
 * security/usuario-sistema
 * security/app-user-details-service
 * config.token/custom-token-enhancer
 * config/oauth-security-config
 * config/resource-server-config
 * config/authorization-server-config
 */

/*
 * .../oauth/token -> username={}&password={}&grant_type=password
 * 
 *  Para usar sem refresh token pelo cookie:
 *  .../oauth/token -> grant_type=refresh_token & refresh_token={código-retornado-na-request-password}
 *  
 *  Para usar com refresh token pelo cookie
 *  - TokenResouce 
 *  - RefreshTokenCookiePreProcessorFilter
 *  - RefreshTokenPostProcessor 
 * .../oauth/token -> grant_type=refresh_token
 */