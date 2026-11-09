package com.baz.divisas.api.tef.sucursales.model;

import java.util.List;

/**
 * The Class SucursalDivAux.
 */
public class SucursalDivAux {


	/** The id sucursal. */
	private String idSucursal;
	
	/** The fecha modificacion. */
	private String fechaModificacion;
	
	/** The divisas. */
	private List<Divisa> divisas;
	
	/** The fecha ultimo cambio OLP. */
	private String fechaUltimoCambioOLP;
	
	
	/**
	 * Instantiates a new sucursal div aux.
	 */
	public SucursalDivAux() {
		super();
	}

	/**
	 * Gets the id sucursal.
	 *
	 * @return the id sucursal
	 */
	public String getIdSucursal() {
		return idSucursal;
	}

	/**
	 * Sets the id sucursal.
	 *
	 * @param idSucursal the new id sucursal
	 */
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}

	/**
	 * Gets the fecha modificacion.
	 *
	 * @return the fecha modificacion
	 */
	public String getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * Sets the fecha modificacion.
	 *
	 * @param fechaModificacion the new fecha modificacion
	 */
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * Gets the divisas.
	 *
	 * @return the divisas
	 */
	public List<Divisa> getDivisas() {
		return divisas;
	}

	/**
	 * Sets the divisas.
	 *
	 * @param divisas the new divisas
	 */
	public void setDivisas(List<Divisa> divisas) {
		this.divisas = divisas;
	}

	/**
	 * Gets the fecha ultimo cambio OLP.
	 *
	 * @return the fecha ultimo cambio OLP
	 */
	public String getFechaUltimoCambioOLP() {
		return fechaUltimoCambioOLP;
	}

	/**
	 * Sets the fecha ultimo cambio OLP.
	 *
	 * @param fechaUltimoCambioOLP the new fecha ultimo cambio OLP
	 */
	public void setFechaUltimoCambioOLP(String fechaUltimoCambioOLP) {
		this.fechaUltimoCambioOLP = fechaUltimoCambioOLP;
	}

}
