package com.baz.divisas.api.core.sucursales.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase BeanTopesCaja
 * 
 * @author 1096526
 *
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class BeanTopesCaja {

	private int idDivisa;
	private int idSucursal;
	private int importeTope;
	private int idCaja;

	/**
	 * Constructor de la clase
	 */
	public BeanTopesCaja() {
		super();
	}

	/**
	 * @return the idDivisa
	 */
	public int getIdDivisa() {
		return idDivisa;
	}

	/**
	 * @param idDivisa the idDivisa to set
	 */
	public void setIdDivisa(int idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * @return the idSucursal
	 */
	public int getIdSucursal() {
		return idSucursal;
	}

	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}

	/**
	 * @return the importeTope
	 */
	public int getImporteTope() {
		return importeTope;
	}

	/**
	 * @param importeTope the importeTope to set
	 */
	public void setImporteTope(int importeTope) {
		this.importeTope = importeTope;
	}

	/**
	 * @return the idCaja
	 */
	public int getIdCaja() {
		return idCaja;
	}

	/**
	 * @param idCaja the idCaja to set
	 */
	public void setIdCaja(int idCaja) {
		this.idCaja = idCaja;
	}

}
