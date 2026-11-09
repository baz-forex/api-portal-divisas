package com.baz.divisas.api.core.sucursales.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Titulo
 * 
 * @author 1096526
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Titulo {
	private String texto;
	private String imagen;

	/**
	 * Constructor de la clase
	 */
	public Titulo() {
		super();
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
