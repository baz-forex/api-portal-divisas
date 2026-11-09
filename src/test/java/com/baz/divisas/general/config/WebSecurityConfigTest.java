package com.baz.divisas.general.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.baz.divisas.security.jwt.AuthAccessDeniedHandler;
import com.baz.divisas.security.jwt.AuthEntryPointJwt;
import com.baz.divisas.security.services.UserDetailsServiceImpl;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WebSecurityConfigTest {

	@InjectMocks
	public WebSecurityConfig web;

	@Mock
	public WebSecurityConfigurerAdapter sp;
	@Mock
	private ObjectPostProcessor<Object> objectPostProcessor;

	@InjectMocks
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	private HttpSecurity http;

	Map<Class<?>, Object> x = new HashMap<>();

	private WebSecurityConfig webSecurity;

	@Test
	public void test() throws Exception {
		web.authenticationJwtTokenFilter();
		web.configure(authenticationManagerBuilder);
		web.corsFilter();
		http = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder,x );
		http.securityContext();
	}

	@Test
	public void loadUser() throws Exception {
		UserDetailsServiceImpl userDetailsService = mock(UserDetailsServiceImpl.class);
		AuthEntryPointJwt unauthorizedHandler = mock(AuthEntryPointJwt.class);
		AuthAccessDeniedHandler accessDeniedHandler = mock(AuthAccessDeniedHandler.class);

		webSecurity = spy(new WebSecurityConfig(userDetailsService, unauthorizedHandler, accessDeniedHandler));

	}
}

