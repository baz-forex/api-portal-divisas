package com.baz.divisas.api.tef.regionessucursales.dto;

import java.util.Collections;
import java.util.List;

import com.baz.divisas.api.tef.general.model.SucursalAux;

/**
 * The Class RegionSucursal.
 */
public class RegionSucursal {

	/** The id region. */
	private String idRegion;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The sucursales. */
	private List<SucursalAux> sucursales= Collections.emptyList();
	
	/**
	 * Instantiates a new region sucursal.
	 */
	public RegionSucursal() {
		super();
	}
	
	/**
	 * Gets the id region.
	 *
	 * @return the id region
	 */
	public String getIdRegion() {
		return idRegion;
	}
	
	/**
	 * Sets the id region.
	 *
	 * @param idRegion the new id region
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
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
