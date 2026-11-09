package com.baz.divisas.api.core.configuraciones.dto;

/**
 * Clase ReqCampoRestringido
 * @author B813196
 * itoxtle
 */
public class ReqCampoRestringido {

	private String idCampo;
	private String restriccion;

	/**
	 * Constructor de la clase
	 */
	public ReqCampoRestringido() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param idCampo - Identificador del campo
	 * @param restriccion - Restriccion del campo
	 * @param motivo - Motivo de cambio
	 */
	public ReqCampoRestringido(String idCampo, String restriccion) {
		super();
		this.idCampo = idCampo;
		this.restriccion = restriccion;
	}
	
	public String getIdCampo() {
		return idCampo;
	}
	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}
	public String getRestriccion() {
		return restriccion;
	}
	public void setRestriccion(String restriccion) {
		this.restriccion = restriccion;
	}
	
}
