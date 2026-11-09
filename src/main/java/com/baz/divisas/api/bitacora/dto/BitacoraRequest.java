package com.baz.divisas.api.bitacora.dto;

/**
 * Clase BitacoraRequest
 * @author B813196
 * itoxte
 */
public class BitacoraRequest {

	private String idModulo;
	private String fechaInicio;
	private String fechaFin;
	private String idUsuario;
	
	/**
	 * Constructor de la clase
	 */
	public BitacoraRequest() {
		super();
	}
	
	public String getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(String idModulo) {
		this.idModulo = idModulo;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
