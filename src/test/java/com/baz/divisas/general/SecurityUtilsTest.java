package com.baz.divisas.general;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.api.usuarios.dao.UsuariosDao;
import com.baz.divisas.api.usuarios.model.Usuario;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SecurityUtilsTest {

	@InjectMocks
	private SecurityUtils securityService;
	@Mock
	private UsuariosDao usuariosDao;
	@Mock
	private Authentication authentication;
	@Mock
	private SecurityContext securityContext;
	
	@Test
	void testGetUserSesion() {
		
		SecurityContextHolder.setContext(securityContext);
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdUserSesion("123");
		
		when(securityContext.getAuthentication()).thenReturn(authentication);	
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
		when(usuariosDao.findById(isA(String.class))).thenReturn(null);
		
		UserDetailsImpl resultado = securityService.getUserSesion();
		assertThat(resultado).isNotNull();
		
		///////////
		when(usuariosDao.findById(isA(String.class))).thenReturn(new Usuario());
		resultado = securityService.getUserSesion();
		assertThat(resultado).isNotNull();
		
	}
	
	@Test
	void testSetUserSesion() {
		securityService.setUserSesion(authentication);
		assertThat(true).isTrue();
	}
	
}
