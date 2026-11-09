package com.baz.divisas.api.tef.regiones.model;

/**
 * Clase Estrategia
 * 
 * @author 1027346 ymartinezb
 */
public class Estrategia {

	private boolean activo;
	private Double monto;
	private String idBitacoraAlta;
	private String idBitacoraActualizacion;

	/**
	 * Constructor de la clase
	 * 
	 * @param activo - Estatus
	 * @param monto - Monto
	 * @param idBitacoraAlta - Bitacora
	 * @param idBitacoraActualizacion - Bitacora
	 */
	public Estrategia(boolean activo, Double monto, String idBitacoraAlta, String idBitacoraActualizacion) {
		super();
		this.activo = activo;
		this.monto = monto;
		this.idBitacoraAlta = idBitacoraAlta;
		this.idBitacoraActualizacion = idBitacoraActualizacion;
	}

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
	public Double getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
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
