package com.baz.divisas.api.tef.dashboard.dto;

import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

/**
 * RequestRegionTC.
 */
public class RequestRegionTC {

	@BsonRepresentation(BsonType.OBJECT_ID)
	@NotNull(message = "Requerido")
	private String id;
	@NotNull(message = "Requerido")
	private String tipoOperacionTC;
	@NotNull(message = "Requerido")
	private String divisa;
	@NotNull(message = "Requerido")
	private Double montoTC;
	@NotNull(message = "Requerido")
	private boolean activo;

	/**
	 * Constructor de la clase
	 */
	public RequestRegionTC() {
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
	 * @return the tipoOperacionTC
	 */
	public String getTipoOperacionTC() {
		return tipoOperacionTC;
	}
	/**
	 * @param tipoOperacionTC the tipoOperacionTC to set
	 */
	public void setTipoOperacionTC(String tipoOperacionTC) {
		this.tipoOperacionTC = tipoOperacionTC;
	}
	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}
	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	/**
	 * @return the montoTC
	 */
	public Double getMontoTC() {
		return montoTC;
	}
	/**
	 * @param montoTC the montoTC to set
	 */
	public void setMontoTC(Double montoTC) {
		this.montoTC = montoTC;
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

}
