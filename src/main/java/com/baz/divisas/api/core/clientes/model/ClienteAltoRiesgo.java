package com.baz.divisas.api.core.clientes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ClienteAltoRiesgo.
 */
public class ClienteAltoRiesgo {

	/** The id. */
	private String id;
	
	/** The nombre. */
	@JsonProperty("nombre")
	private String nombre;
	
	/** The apellido paterno. */
	@JsonProperty("apellidoPaterno")
	private String apellidoPaterno;
	
	/** The apellido materno. */
	@JsonProperty("apellidoMaterno")
	private String apellidoMaterno;
	
	/** The fecha nacimiento. */
	@JsonProperty("fechaNacimiento")
	private String fechaNacimiento;
	
	
	/**
	 * Instantiates a new cliente alto riesgo.
	 */
	public ClienteAltoRiesgo() {
		super();
	}

	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Gets the apellido paterno.
	 *
	 * @return the apellido paterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
	/**
	 * Sets the apellido paterno.
	 *
	 * @param apellidoPaterno the new apellido paterno
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	/**
	 * Gets the apellido materno.
	 *
	 * @return the apellido materno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
	/**
	 * Sets the apellido materno.
	 *
	 * @param apellidoMaterno the new apellido materno
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	/**
	 * Gets the fecha nacimiento.
	 *
	 * @return the fecha nacimiento
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	/**
	 * Sets the fecha nacimiento.
	 *
	 * @param fechaNacimiento the new fecha nacimiento
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
}
