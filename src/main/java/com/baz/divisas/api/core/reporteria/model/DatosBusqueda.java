package com.baz.divisas.api.core.reporteria.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346
 * Martinez Yonathan
 *
 */
public class DatosBusqueda implements Serializable{

	
	/**
	 *
	 */
	private static final long serialVersionUID = 6957720021972656881L;
	
	private String nombre;
	@SerializedName("apellido_paterno")
	private String apellidoPaterno;
	@SerializedName("apellido_materno")
	private String apellidoMaterno;
	@SerializedName("fecha_nacimiento")
	private String fechaNacimiento;
	
	/**
	 * Constructor de la clase
	 */
	public DatosBusqueda() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
