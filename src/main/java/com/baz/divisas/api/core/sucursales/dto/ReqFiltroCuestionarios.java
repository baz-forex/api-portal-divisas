package com.baz.divisas.api.core.sucursales.dto;

/**
 * Clase ReqPaginado
 * 
 * @author 1096526
 *
 */
public class ReqFiltroCuestionarios {
	private int mes;
	private int anio;
	private String sucursal;

	/**
	 * Constructor de la clase
	 */
	public ReqFiltroCuestionarios() {
		super();
	}

	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}

	/**
	 * @return the anio
	 */
	public int getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(int anio) {
		this.anio = anio;
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
