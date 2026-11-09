package com.baz.divisas.api.usuarios.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Modulo de accesos
 * 
 * @author B813196
 *
 */
public class Modulo {

	@NotBlank(message = "Requerido")
	@ApiModelProperty(value = "Id modulo", position = 1)
	@BsonProperty("id")
	private String id;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value = "Permisos del modulo", position = 2)
	private Permisos permisos;
	
	/**
	 * Constructor de la clase
	 */
	public Modulo() {
		super();
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param id - Id del modulo
	 * @param permisos - Permisos de acceso
	 */
	public Modulo(String id, Permisos permisos) {
		super();
		this.id = id;
		this.permisos = permisos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Permisos getPermisos() {
		return permisos;
	}

	public void setPermisos(Permisos permisos) {
		this.permisos = permisos;
	}

}
