package com.baz.divisas.api.tef.sucursales.model;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * TipoOperacion.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoOperacion {

	/** The id tipo operacion. */
	private String idTipoOperacion;
	
	/** The tipo cambio spot. */
	private Double tipoCambioSpot;
	
	/** The tipo cambio spread. */
	private Double tipoCambioSpread =0.0d;
	
	/**
	 * Constructor de la clase.
	 */
	public TipoOperacion() {
		super();

	}
	
	
	public TipoOperacion(String idTipoOperacion,Double tipoCambioSpot,Double tipoCambioSpread) {
		super();
		this.idTipoOperacion = idTipoOperacion;
		this.tipoCambioSpot = tipoCambioSpot;
		this.tipoCambioSpread = tipoCambioSpread;
	}
	/**
	 * Gets the id tipo operacion.
	 *
	 * @return the idTipoOperacion
	 */
	public String getIdTipoOperacion() {
		return idTipoOperacion;
	}
	
	/**
	 * Sets the id tipo operacion.
	 *
	 * @param idTipoOperacion the idTipoOperacion to set
	 */
	public void setIdTipoOperacion(String idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}
	
	/**
	 * Gets the tipo cambio spot.
	 *
	 * @return the tipoCambioSpot
	 */
	public Double getTipoCambioSpot() {
		DecimalFormat formatoDosDecimales = new DecimalFormat("0");
		formatoDosDecimales.setMinimumFractionDigits(2);
		formatoDosDecimales.setMaximumFractionDigits(2);		
		return Double.valueOf(formatoDosDecimales.format(tipoCambioSpot));
	}
	
	/**
	 * Sets the tipo cambio spot.
	 *
	 * @param tipoCambioSpot the tipoCambioSpot to set
	 */
	public void setTipoCambioSpot(Double tipoCambioSpot) {
		this.tipoCambioSpot = tipoCambioSpot;
	}

	/**
	 * Gets the tipo cambio spread.
	 *
	 * @return the tipo cambio spread
	 */
	public Double getTipoCambioSpread() {
		DecimalFormat formatoDosDecimales = new DecimalFormat("0");
		formatoDosDecimales.setMinimumFractionDigits(2);
		formatoDosDecimales.setMaximumFractionDigits(2);		
		return Double.valueOf(formatoDosDecimales.format(tipoCambioSpread));
	}

	/**
	 * Sets the tipo cambio spread.
	 *
	 * @param tipoCambioSpread the new tipo cambio spread
	 */
	public void setTipoCambioSpread(Double tipoCambioSpread) {
		this.tipoCambioSpread = tipoCambioSpread;
	}



}
