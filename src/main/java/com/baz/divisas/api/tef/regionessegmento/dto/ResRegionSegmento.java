package com.baz.divisas.api.tef.regionessegmento.dto;

import java.util.Collections;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Clase TiposOperacion
 * 
 * @author 1027346 ymartinezb
 */
public class ResRegionSegmento {

	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String descripcion;
	private boolean activo;
	private List<String> segmentos = Collections.emptyList();
	
	/**
	 * @param id
	 * @param descripcion
	 * @param activo
	 * @param segmentos
	 */
	public ResRegionSegmento(String id, String descripcion, boolean activo, List<String> segmentos) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.activo = activo;
		this.segmentos = segmentos;
	}

	/**
	 * 
	 */
	public ResRegionSegmento() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the segmentos
	 */
	public List<String> getSegmentos() {
		return segmentos;
	}

	/**
	 * @param segmentos the segmentos to set
	 */
	public void setSegmentos(List<String> segmentos) {
		this.segmentos = segmentos;
	}
	
}
