package com.baz.divisas.api.apigee.dto;

import java.io.Serializable;
import java.util.List;

public class TokenApigee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 904755917861701766L;
	private String access_token;
	private String codigo;
	private String mensaje;
	private List<String> detalles;
	private String folio;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String accessToken) {
		this.access_token = accessToken;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<String> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<String> detalles) {
		this.detalles = detalles;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	@Override
	public String toString() {
		return "TokenApigee [access_token=" + access_token + ", codigo=" + codigo + ", mensaje=" + mensaje
				+ ", detalles=" + detalles + ", folio=" + folio + "]";
	}
	
}
