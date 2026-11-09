package com.baz.divisas.api.core.sucursales.model;

/**
 * Clase ConsultaRespuestas
 * 
 * @author 1096526
 *
 */
public class ConsultaRespuestas {
	private String sucursal;
	private int cuestionarios;
	private int avance;
	private String gerente;
	private Contacto contacto;
	private String estatus;
	private boolean certificar;

	/**
	 * Constructor de la clase
	 * 
	 * @param preguntas - lista con los campos de las preguntas
	 */
	public ConsultaRespuestas() {
		super();
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

	/**
	 * @return the cuestionarios
	 */
	public int getCuestionarios() {
		return cuestionarios;
	}

	/**
	 * @param cuestionarios the cuestionarios to set
	 */
	public void setCuestionarios(int cuestionarios) {
		this.cuestionarios = cuestionarios;
	}

	/**
	 * @return the avance
	 */
	public int getAvance() {
		return avance;
	}

	/**
	 * @param avance the avance to set
	 */
	public void setAvance(int avance) {
		this.avance = avance;
	}

	/**
	 * @return the gerente
	 */
	public String getGerente() {
		return gerente;
	}

	/**
	 * @param gerente the gerente to set
	 */
	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	/**
	 * @return the contacto
	 */
	public Contacto getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the certificar
	 */
	public boolean isCertificar() {
		return certificar;
	}

	/**
	 * @param certificar the certificar to set
	 */
	public void setCertificar(boolean certificar) {
		this.certificar = certificar;
	}

}
