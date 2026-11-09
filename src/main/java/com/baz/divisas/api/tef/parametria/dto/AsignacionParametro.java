package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class AsignacionParametro.
 */
public class AsignacionParametro {

	/** The id. */
	private String id;
	
	/** The id parametro. */
	private String idParametro;
	
	/** The parametro. */
	private String parametro;
	
	/** The asignacion. */
	private String asignacion;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The sucursales. */
	private List<String> sucursales = new ArrayList<>();
	
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
	 * Gets the id parametro.
	 *
	 * @return the id parametro
	 */
	public String getIdParametro() {
		return idParametro;
	}
	
	/**
	 * Sets the id parametro.
	 *
	 * @param idParametro the new id parametro
	 */
	public void setIdParametro(String idParametro) {
		this.idParametro = idParametro;
	}
	
	/**
	 * Gets the parametro.
	 *
	 * @return the parametro
	 */
	public String getParametro() {
		return parametro;
	}
	
	/**
	 * Sets the parametro.
	 *
	 * @param parametro the new parametro
	 */
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	/**
	 * Gets the asignacion.
	 *
	 * @return the asignacion
	 */
	public String getAsignacion() {
		return asignacion;
	}
	
	/**
	 * Sets the asignacion.
	 *
	 * @param asignacion the new asignacion
	 */
	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
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
	 * Gets the sucursales.
	 *
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return sucursales;
	}
	
	/**
	 * Sets the sucursales.
	 *
	 * @param sucursales the new sucursales
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = sucursales;
	}
	
	
}
