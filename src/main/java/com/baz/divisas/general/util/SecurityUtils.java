package com.baz.divisas.general.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.usuarios.dao.UsuariosDao;
import com.baz.divisas.api.usuarios.model.Usuario;
import com.baz.divisas.security.services.UserDetailsImpl;

/**
 * Clase SecurityUtils
 * @author B813196
 * itoxtle
 */
@Service
public class SecurityUtils {
		
	private UsuariosDao usuariosDao;
	
	@Autowired
	private SecurityUtils(UsuariosDao usuariosDao) {
		super();
		this.usuariosDao = usuariosDao;
	}

	/**
	 * Obtiene el usuario en sesion
	 * @return UserDetailsImpl
	 */
	public UserDetailsImpl getUserSesion() {
		
		UserDetailsImpl userSesion =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Usuario usuario = usuariosDao.findById(userSesion.getIdUserSesion());
		
		if(usuario != null) {
			userSesion.setNombre(usuario.getNombre());
			userSesion.setApellidoPaterno(usuario.getApellidoPaterno());
			userSesion.setApellidoMaterno(usuario.getApellidoMaterno());
		}

		
		return userSesion;
	}
	
	/**
	 * Asigna el usuario a la sesion
	 * @param authentication - Autenticacion
	 */
	public void setUserSesion(Authentication authentication) {		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
