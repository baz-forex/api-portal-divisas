package com.baz.divisas.api.tef.regiones.model;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Divisa.
 *
 * @author 1027346 ymartinezb
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Divisa {

	/** The id. */
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String _id;
	
	/** The id. */
	@NotNull(message = "Requerido")
	private String id;
	
	/** The descripcion. */
	@NotNull(message = "Requerido")
	private String descripcion;
	
	/** The activo. */
	private boolean activo;
	
	/** The tipos operacion. */
	private List<TipoOperacion> tiposOperacion = Collections.emptyList();

	/**
	 * Constructor de la clase.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @param tiposOperacion - Tipos de Operacion
	 * @param activo - Estatus
	 */
	public Divisa(String id, @NotNull(message = "Requerido") String descripcion, 
			List<TipoOperacion> tiposOperacion, boolean activo) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.tiposOperacion = tiposOperacion;
		this.activo = activo;
	}

	/**
	 * Constructor de la clase.
	 */
	public Divisa() {
		super();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * Sets the id.
	 *
	 * @param _id the new id
	 */
	public void set_id(String _id) {
		this._id = _id;
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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Gets the tipos operacion.
	 *
	 * @return the tiposOperacion
	 */
	public List<TipoOperacion> getTiposOperacion() {
		return tiposOperacion;
	}

	/**
	 * Sets the tipos operacion.
	 *
	 * @param tiposOperacion the tiposOperacion to set
	 */
	public void setTiposOperacion(List<TipoOperacion> tiposOperacion) {
		this.tiposOperacion = tiposOperacion;
	}

	/**
	 * Checks if is activo.
	 *
	 * @return the status
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
	
}
