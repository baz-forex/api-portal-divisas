package com.baz.divisas.api.tef.general.model;

/**
 * Estrategia.
 */
public class Estrategia {

	private boolean activo;
	private double monto;
	private String idBitacoraAlta;
	private String idBitacoraActualizacion;

	/**
	 * Constructor de la clase
	 */
	public Estrategia() {

		super();
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
	/**
	 * @return the monto
	 */
	public double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}
	/**
	 * @return the idBitacoraAlta
	 */
	public String getIdBitacoraAlta() {
		return idBitacoraAlta;
	}
	/**
	 * @param idBitacoraAlta the idBitacoraAlta to set
	 */
	public void setIdBitacoraAlta(String idBitacoraAlta) {
		this.idBitacoraAlta = idBitacoraAlta;
	}
	/**
	 * @return the idBitacoraActualizacion
	 */
	public String getIdBitacoraActualizacion() {
		return idBitacoraActualizacion;
	}
	/**
	 * @param idBitacoraActualizacion the idBitacoraActualizacion to set
	 */
	public void setIdBitacoraActualizacion(String idBitacoraActualizacion) {
		this.idBitacoraActualizacion = idBitacoraActualizacion;
	}



}
