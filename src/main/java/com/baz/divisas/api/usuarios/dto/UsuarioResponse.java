package com.baz.divisas.api.usuarios.dto;

import com.baz.divisas.api.usuarios.model.Menu;
import com.baz.divisas.api.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase
 * Response 
 * Consulta Usuario
 * 
 * @author 
 * B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UsuarioResponse {

	private Usuario usuario;
	private Menu perfilAccesos;

	/**
	 * Constructor de la clase
	 * @param usuario - Usuario
	 * @param perfilAccesos - Perfil de accesos
	 */
	public UsuarioResponse(Usuario usuario, Menu perfilAccesos) {
		super();
		this.usuario = usuario;
		this.perfilAccesos = perfilAccesos;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Menu getPerfilAccesos() {
		return perfilAccesos;
	}
	public void setPerfilAccesos(Menu perfilAccesos) {
		this.perfilAccesos = perfilAccesos;
	}
	
}
