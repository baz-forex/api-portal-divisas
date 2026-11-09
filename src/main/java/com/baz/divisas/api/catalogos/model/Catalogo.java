package com.baz.divisas.api.catalogos.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase Catalogo
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Catalogo {

	@SerializedName(value="_id", alternate="id")
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Id", position=1, required=true)
	private String id;
	@ApiModelProperty(value="Descripcion", position=2, required=true)
	private String descripcion;
	@ApiModelProperty(value="Activo", position=3, required=true)
	@NotNull(message = "Requerido")
	private boolean activo;

	/**
	 * Constructor de la clase
	 */
	public Catalogo() {
		super();
	}

	/**
	 * Constructor de la Clase
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @param activo - estatus
	 */
	public Catalogo(@NotNull(message = "Requerido") String id, String descripcion,
			@NotNull(message = "Requerido") boolean activo) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.activo = activo;
	}
	
	/**
	 * Constructor de la Clase
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @param activo - estatus
	 */
	public Catalogo(@NotNull(message = "Requerido") String id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
