package com.baz.divisas.api.siex.configuraciones.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase RestriccionCampo.
 *
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Restriccion {

	/** The activo. */
	private boolean activo;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The palabras. */
	private List<String> palabras = new ArrayList<>();
	
	/**
	 * Constructor de la clase.
	 */
	public Restriccion() {
		super();

	}

	/**
	 * Checks if is activo.
	 *
	 * @return true, if is activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Sets the activo.
	 *
	 * @param activo the new activo
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the palabras.
	 *
	 * @return the palabras
	 */
	public List<String> getPalabras() {
		return new ArrayList<>(palabras);
	}

	/**
	 * Sets the palabras.
	 *
	 * @param palabras the new palabras
	 */
	public void setPalabras(List<String> palabras) {
		this.palabras = new ArrayList<>(palabras);
	}
	
}
