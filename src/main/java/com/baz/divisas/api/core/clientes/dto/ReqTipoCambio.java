package com.baz.divisas.api.core.clientes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ReqTipoCambio.
 */
public class ReqTipoCambio {
	private String uid = "01001000000000000000";
	@JsonProperty("id_pais")
	private String idPais = "1";
	@JsonProperty("id_negocio")
	private String idNegocio = "DIV";
	private String sucursal;
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the idPais
	 */
	public String getIdPais() {
		return idPais;
	}
	/**
	 * @param idPais the idPais to set
	 */
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	/**
	 * @return the idNegocio
	 */
	public String getIdNegocio() {
		return idNegocio;
	}
	/**
	 * @param idNegocio the idNegocio to set
	 */
	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}
	/**
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}
	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	
}
