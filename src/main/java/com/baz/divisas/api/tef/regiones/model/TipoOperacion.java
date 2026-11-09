
package com.baz.divisas.api.tef.regiones.model;

import java.text.DecimalFormat;

import javax.validation.constraints.NotNull;

import com.baz.divisas.api.tef.general.model.Limites;

/**
 * Clase TipoOperacion.
 *
 * @author 1027346 ymartinezb
 */
public class TipoOperacion {

	/** The id tipo operacion. */
	@NotNull(message = "Requerido")
	private String idTipoOperacion;
	
	/** The descripcion. */
	@NotNull(message = "Requerido")
	private String descripcion;
	
	/** The tipo cambio spot. */
	private Double tipoCambioSpot;
	
	/** The tipo cambio spread. */
	private Double tipoCambioSpread;
	
	/** The estrategia. */
	private Estrategia estrategia;
	
	/** The limites. */
	private Limites limites;
	
	/** The activo. */
	private boolean activo;

	/**
	 * Constructor de la clase.
	 */
	public TipoOperacion() {
		super();
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param idTipoOperacion - Id
	 * @param descripcion - Descripcion
	 * @param tipoCambioSpot - Tipo de Cambio
	 * @param estrategia - Estrategia
	 * @param limites - Limites
	 * @param activo - Estatus
	 */
	public TipoOperacion(String idTipoOperacion, String descripcion, Double tipoCambioSpot,Double tipoCambioSpread, Estrategia estrategia,
			Limites limites, boolean activo) {
		super();
		this.idTipoOperacion = idTipoOperacion;
		this.descripcion = descripcion;
		this.tipoCambioSpot = tipoCambioSpot;
		this.tipoCambioSpread = tipoCambioSpread;
		this.estrategia = estrategia;
		this.limites = limites;
		this.activo = activo;
	}

	/**
	 * Constructor de la clase.
	 *
	 * @param idTipoOperacion - Id
	 * @param descripcion - Descripcion
	 * @param tipoCambioSpot - Tipo de Cambio
	 * @param estrategia - Estrategia
	 * @param limites - Limites
	 * @param activo - Estatus
	 */
	public TipoOperacion(String idTipoOperacion, String descripcion, Double tipoCambioSpot) {
		super();
		this.idTipoOperacion = idTipoOperacion;
		this.descripcion = descripcion;
		this.tipoCambioSpot = tipoCambioSpot;
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
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * Gets the estrategia.
	 *
	 * @return the estrategia
	 */
	public Estrategia getEstrategia() {
		return estrategia;
	}

	/**
	 * Sets the estrategia.
	 *
	 * @param estrategia the estrategia to set
	 */
	public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}

	/**
	 * Gets the limites.
	 *
	 * @return the limites
	 */
	public Limites getLimites() {
		return limites;
	}

	/**
	 * Sets the limites.
	 *
	 * @param limites the limites to set
	 */
	public void setLimites(Limites limites) {
		this.limites = limites;
	}

	/**
	 * Checks if is activo.
	 *
	 * @return the estatus
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Sets the activo.
	 *
	 * @param activo the new activo
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
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
