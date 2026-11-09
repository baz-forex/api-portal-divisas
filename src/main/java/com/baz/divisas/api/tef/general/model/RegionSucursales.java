package com.baz.divisas.api.tef.general.model;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * The Class RegionSucursales.
 */
public class RegionSucursales {

	/** The id region. */
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idRegion;
	
	/** The descripcion. */
	@NotNull(message = "Requerido")
	private String descripcion;
    
    /** The sucursales. */
    private List<Sucursal> sucursales = Collections.emptyList();
    
    public RegionSucursales() {
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
	public List<Sucursal> getSucursales() {
		return sucursales;
	}
	
	/**
	 * Sets the sucursales.
	 *
	 * @param sucursales the new sucursales
	 */
	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}
	
	
}
