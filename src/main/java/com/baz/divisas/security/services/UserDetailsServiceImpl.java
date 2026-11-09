package com.baz.divisas.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.auth.dao.AuthClientDao;
import com.baz.divisas.api.auth.model.AuthClient;

/**
 * Service Implementacion
 * UserDetailsServiceImpl
 * Implements UserDetailsService
 * Utilizado por AutenticationManager
 * para autenticar al Usuario
 * @author B813196
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Override
  public UserDetailsImpl loadUserByUsername(String idUsuario) {

	  AuthClient client = new AuthClientDao().findById(idUsuario);
	  
	  if(null == client) {
		  throw new UsernameNotFoundException("Usuario no encontrado.");
	  }

    return new UserDetailsImpl(client);
  }

}
