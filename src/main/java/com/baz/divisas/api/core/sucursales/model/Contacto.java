package com.baz.divisas.api.core.sucursales.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Contacto
 * 
 * @author 1096526
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contacto {
	private String telefono;
	private String correo;

	/**
	 * Constructor de la clase
	 */
	public Contacto() {
		super();
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
