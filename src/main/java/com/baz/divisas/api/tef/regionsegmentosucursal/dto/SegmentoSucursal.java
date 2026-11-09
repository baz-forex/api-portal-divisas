package com.baz.divisas.api.tef.regionsegmentosucursal.dto;

import java.util.Collections;
import java.util.List;

import com.baz.divisas.api.tef.general.model.SucursalAux;

/**
 * The Class SegmentoSucursal.
 */
public class SegmentoSucursal {

	/** The id segmento. */
	private String idSegmento;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The sucursales. */
	private List<SucursalAux> sucursales= Collections.emptyList();
	
	/**
	 * Instantiates a new segmento sucursal.
	 */
	public SegmentoSucursal() {
		super();
	}

	/**
	 * Gets the id segmento.
	 *
	 * @return the id segmento
	 */
	public String getIdSegmento() {
		return idSegmento;
	}

	/**
	 * Sets the id segmento.
	 *
	 * @param idSegmento the new id segmento
	 */
	public void setIdSegmento(String idSegmento) {
		this.idSegmento = idSegmento;
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
	public List<SucursalAux> getSucursales() {
		return sucursales;
	}

	/**
	 * Sets the sucursales.
	 *
	 * @param sucursales the new sucursales
	 */
	public void setSucursales(List<SucursalAux> sucursales) {
		this.sucursales = sucursales;
	}
	
}
