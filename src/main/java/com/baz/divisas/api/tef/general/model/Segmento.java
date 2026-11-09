package com.baz.divisas.api.tef.general.model;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * Clase Regiones
 * 
 * @author 164945 ogatica
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Segmento {

	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;

	@NotNull(message = "Requerido")
	private String descripcion;

	private boolean activo;
	private boolean asignado;
	private List<Divisa> divisas = Collections.emptyList();	
	private List<String> sucursales = Collections.emptyList();
	
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String idRegion;


	/**
	 * Constructor de la clase
	 * 
	 * @param descripcion - Descripcion
	 * @param activo - Activo
	 * @param divisas - Divisas
	 */
	public Segmento(@NotNull(message = "Requerido") String descripcion, boolean activo,
			List<Divisa> divisas) {
		super();
		this.descripcion = descripcion;
		this.activo = activo;
		this.divisas = divisas;
	}

	/**
	 * Constructor de la clase
	 */
	public Segmento() {
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

	/**
	 * @return the asignado
	 */
	public boolean isAsignado() {
		return asignado;
	}

	/**
	 * @param asignado the asignado to set
	 */
	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	/**
	 * @return the idRegion
	 */
	public String getIdRegion() {
		return idRegion;
	}

	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}

}
