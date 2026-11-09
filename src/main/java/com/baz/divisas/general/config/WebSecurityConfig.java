package com.baz.divisas.general.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.baz.divisas.security.jwt.AuthAccessDeniedHandler;
import com.baz.divisas.security.jwt.AuthEntryPointJwt;
import com.baz.divisas.security.jwt.AuthTokenFilter;
import com.baz.divisas.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
				// securedEnabled = true,
				// jsr250Enabled = true,
				prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl userDetailsService;
	private AuthEntryPointJwt unauthorizedHandler;
	private AuthAccessDeniedHandler accessDeniedHandler;

	private static final String[] PUBLIC_ENDPOINTS = {"/**/token", "/**/validaEstatus", "/**/auth/client", "/v2/api-docs",
					"/swagger-resources/**", "/swagger-ui.html", "/configuration/**", "/webjars/**", "/public", "/index.jsp"};

	@Autowired
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler,
	                         AuthAccessDeniedHandler accessDeniedHandler) {
		super();
		this.userDetailsService = userDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
						.and().csrf().disable()
						.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(unauthorizedHandler).and()
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
						.authorizeRequests().antMatchers(PUBLIC_ENDPOINTS).permitAll()
						.antMatchers("/**/scim/**").access("hasRole('ROLE_DSI_SCIM')")
						.antMatchers("/**/api/**").access("hasRole('ROLE_DIVISAS_PORTAL')")
						.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source =
						new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setExposedHeaders(List.of("Content-Disposition"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
