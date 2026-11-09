package com.baz.divisas.api.core.clientes.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase Banderas de Control
 * @author B813196
 *
 */
public class BanderasControl {

	private Bandera digitalizacion;
	@JsonProperty("expedienteActualizado")
	@JsonAlias("expediente_actualizado")
	private Bandera expedienteActualizado;
	@JsonProperty("cuestionarioPeps")
	@JsonAlias("cuestionario_peps")
	private Bandera cuestionarioPeps;
	@JsonProperty("cuestionarioPld")
	@JsonAlias("cuestionario_pld")
	private Bandera cuestionarioPld;
	
	/**
	 * Constructor de la clase
	 */
	public BanderasControl() {
		super();
	}
	
	public Bandera getDigitalizacion() {
		return digitalizacion;
	}
	public void setDigitalizacion(Bandera digitalizacion) {
		this.digitalizacion = digitalizacion;
	}
	public Bandera getExpedienteActualizado() {
		return expedienteActualizado;
	}
	public void setExpedienteActualizado(Bandera expedienteActualizado) {
		this.expedienteActualizado = expedienteActualizado;
	}
	public Bandera getCuestionarioPeps() {
		return cuestionarioPeps;
	}
	public void setCuestionarioPeps(Bandera cuestionarioPeps) {
		this.cuestionarioPeps = cuestionarioPeps;
	}
	public Bandera getCuestionarioPld() {
		return cuestionarioPld;
	}
	public void setCuestionarioPld(Bandera cuestionarioPld) {
		this.cuestionarioPld = cuestionarioPld;
	}
}
