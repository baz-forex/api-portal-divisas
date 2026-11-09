package com.baz.divisas.api.tef.regiones.model;

/**
 * Clase TiposOperacion
 * 
 * @author 1027346 ymartinezb
 */
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;


import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

/**
 * Clase Region
 * 
 * @author 164945 ogatica
 */
public class Region {

	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;

	@NotNull(message = "Requerido")
	private String descripcion;

	private boolean activo;
	private List<Divisa> divisas = Collections.emptyList();
	private List<ObjectId> segmentos = Collections.emptyList();
	private List<String> sucursales = Collections.emptyList();

	/**
	 * Constructor de la clase
	 */
	public Region() {
		super();
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param descripcion - Descripcion
	 * @param activo - Estatus
	 * @param divisas - Divisas
	 */
	public Region(@NotNull(message = "Requerido") String descripcion, boolean activo, List<Divisa> divisas) {
		super();
		this.descripcion = descripcion;
		this.activo = activo;
		this.divisas = divisas;
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
	 * @return the divisas
	 */
	public List<Divisa> getDivisas() {
		return divisas;
	}

	/**
	 * @param divisas the divisas to set
	 */
	public void setDivisas(List<Divisa> divisas) {
		this.divisas = divisas;
	}

	/**
	 * @return the segmentos
	 */
	public List<ObjectId> getSegmentos() {
		return segmentos;
	}

	/**
	 * @param segmentos the segmentos to set
	 */
	public void setSegmentos(List<ObjectId> segmentos) {
		this.segmentos = segmentos;
	}

	/**
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return sucursales;
	}

	/**
	 * @param sucursales the sucursales to set
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = sucursales;
	}

}
