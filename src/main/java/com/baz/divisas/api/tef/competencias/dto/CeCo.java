package com.baz.divisas.api.tef.competencias.dto;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class CeCo {
	
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private Integer idSucursal = 0;
	private String estado = "";
	private String ciudad = "" ;
	private String municipio = "";
	private List<Competencias> competencias = new ArrayList<Competencias>();
	/**
	 * @param idSucursal
	 * @param estado
	 * @param ciudad
	 * @param municipio
	 * @param competencias
	 */
	public CeCo(Integer idSucursal, String estado, String ciudad, String municipio, List<Competencias> competencias) {
		super();
		this.idSucursal = idSucursal;
		this.estado = estado;
		this.ciudad = ciudad;
		this.municipio = municipio;
		this.competencias = competencias;
	}
	/**
	 * 
	 */
	public CeCo() {
		super();
	}
	/**
	 * @return the idSucursal
	 */
	public Integer getIdSucursal() {
		return idSucursal;
	}
	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * @return the competencias
	 */
	public List<Competencias> getCompetencias() {
		return competencias;
	}
	/**
	 * @param competencias the competencias to set
	 */
	public void setCompetencias(List<Competencias> competencias) {
		this.competencias = competencias;
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
	
	
}
