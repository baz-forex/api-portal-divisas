package com.baz.divisas.security.services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.api.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl extends Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;
	@JsonIgnore
	private String password;	
	
	private String idUserSesion;
	
	private String idModulo;

	public UserDetailsImpl(AuthClient client) {
		super();
		this.username = client.getClientId();
		this.password = client.getClientSecret();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+this.username));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdUserSesion() {
		return idUserSesion;
	}

	public void setIdUserSesion(String idUserSesion) {
		this.idUserSesion = idUserSesion;
	}

	public String getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}

}
