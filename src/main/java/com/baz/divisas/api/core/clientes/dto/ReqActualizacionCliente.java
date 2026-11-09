package com.baz.divisas.api.core.clientes.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baz.divisas.api.core.clientes.model.CampoModificado;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase request ReqActualizacionCliente
 * @author B813196
 * itoxtle
 */
public class ReqActualizacionCliente {

	@JsonProperty("id_negocio")
	private String idNegocio = "DIV";
	private String usuario;
	private String ip;
	@JsonProperty("id_cliente")
	private String idCliente;
	@JsonProperty("campos_modificados")
	private List<CampoModificado> camposModificados = new ArrayList<>();
	@JsonProperty("motivo_edicion")
	private String motivoEdicion;

	/**
	 * Constructor de la clase
	 */
	public ReqActualizacionCliente() {
		super();
	}
	
	public String getIdNegocio() {
		return idNegocio;
	}
	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public List<CampoModificado> getCamposModificados() {
		return Collections.unmodifiableList(camposModificados);
	}
	public void setCamposModificados(List<CampoModificado> camposModificados) {
		this.camposModificados = Collections.unmodifiableList(camposModificados);
	}
	public String getMotivoEdicion() {
		return motivoEdicion;
	}
	public void setMotivoEdicion(String motivoEdicion) {
		this.motivoEdicion = motivoEdicion;
	}
	
}
