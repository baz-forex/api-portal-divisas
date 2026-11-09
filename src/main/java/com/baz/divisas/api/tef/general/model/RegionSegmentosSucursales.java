package com.baz.divisas.api.tef.general.model;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class RegionSegmentosSucursales.
 */
public class RegionSegmentosSucursales {
	
	/** The id region. */
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idRegion;
	
	/** The descripcion. */
	@NotNull(message = "Requerido")
	private String descripcion;
    
    /** The sucursales. */
    private List<String> sucursales = Collections.emptyList();
    
    /** The segmentos. */
    private List<SegmentoSucursal> segmentos = Collections.emptyList();
    
    /** The segmentos. */
    @JsonIgnore
    private List<ObjectId> segmentosRegion;
	
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

	/**
	 * Gets the segmentos.
	 *
	 * @return the segmentos
	 */
	public List<SegmentoSucursal> getSegmentos() {
		return segmentos;
	}

	/**
	 * Sets the segmentos.
	 *
	 * @param segmentos the new segmentos
	 */
	public void setSegmentos(List<SegmentoSucursal> segmentos) {
		this.segmentos = segmentos;
	}

	/**
	 * Gets the segmentos region.
	 *
	 * @return the segmentos region
	 */
	public List<ObjectId> getSegmentosRegion() {
		return segmentosRegion;
	}

	/**
	 * Sets the segmentos region.
	 *
	 * @param segmentosRegion the new segmentos region
	 */
	public void setSegmentosRegion(List<ObjectId> segmentosRegion) {
		this.segmentosRegion = segmentosRegion;
	}
	
	
}
