package com.baz.divisas.api.core.clientes.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase Detalle de Cliente
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDetalle extends Cliente {

	@JsonProperty("idGiro")
	@JsonAlias("id_giro")
	private String idGiro;
	@JsonProperty("giro")
	private String giro;
	@JsonProperty("idActividad")
	@JsonAlias("id_actividad")
	private String idActividad;
	@JsonProperty("actividad")
	private String actividad;
	@JsonProperty("idNacionalidad")
	@JsonAlias("id_nacionalidad")
	private String idNacionalidad;
	@JsonProperty("nacionalidad")
	private String nacionalidad;
	@JsonProperty("idPaisNacimiento")
	@JsonAlias("id_pais_nacimiento")
	private String idPaisNacimiento;
	@JsonProperty("paisNacimiento")
	@JsonAlias("pais_nacimiento")
	private String paisNacimiento;
	@JsonProperty("banderasControl")
	@JsonAlias("banderas_control")
	private BanderasControl banderasControl;
	@JsonProperty("fechaRegistro")
	@JsonAlias("fecha_registro")
	private String fechaRegistro;
	@JsonProperty("fechaActualizacion")
	@JsonAlias("fecha_actualizacion")
	private String fechaActualizacion;	
	@JsonProperty("sucursalRegistro")
	@JsonAlias("sucursal_registro")
	private String sucursalRegistro;
	@JsonProperty("usuarioRegistro")
	@JsonAlias("usuario_registro")
	private String usuarioRegistro;
	private String motivoEdicion;
	
	/**
	 * Constructor de la clase
	 */
	public ClienteDetalle() {
		super();
	}

	public String getIdGiro() {
		return idGiro;
	}
	public void setIdGiro(String idGiro) {
		this.idGiro = idGiro;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public String getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(String idActividad) {
		this.idActividad = idActividad;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getIdNacionalidad() {
		return idNacionalidad;
	}
	public void setIdNacionalidad(String idNacionalidad) {
		this.idNacionalidad = idNacionalidad;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getIdPaisNacimiento() {
		return idPaisNacimiento;
	}
	public void setIdPaisNacimiento(String idPaisNacimiento) {
		this.idPaisNacimiento = idPaisNacimiento;
	}
	public String getPaisNacimiento() {
		return paisNacimiento;
	}
	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}
	public BanderasControl getBanderasControl() {
		return banderasControl;
	}
	public void setBanderasControl(BanderasControl banderasControl) {
		this.banderasControl = banderasControl;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getSucursalRegistro() {
		return sucursalRegistro;
	}
	public void setSucursalRegistro(String sucursalRegistro) {
		this.sucursalRegistro = sucursalRegistro;
	}
	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}
	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getMotivoEdicion() {
		return motivoEdicion;
	}
	public void setMotivoEdicion(String motivoEdicion) {
		this.motivoEdicion = motivoEdicion;
	}
}
