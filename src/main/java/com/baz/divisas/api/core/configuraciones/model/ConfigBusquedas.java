package com.baz.divisas.api.core.configuraciones.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * Clase ConfigBusquedas
 * @author B813196
 * itoxtlem
 *
 */
public class ConfigBusquedas {

	@DecimalMin(value="0.0", inclusive = false)
	@DecimalMax(value="100.0")
	private double limiteCoincidenciaNombre;
	@DecimalMin(value="0.0", inclusive = false)
	@DecimalMax(value="100.0")
	private double precisionNombre;
	@DecimalMin(value="0.0", inclusive = false)
	@DecimalMax(value="100.0")
	private double valorPrimarioNombres;
	@DecimalMin(value="0.0", inclusive = false)
	@DecimalMax(value="100.0")
	private double valorPrimarioApellidos;
	@DecimalMin(value="0.0", inclusive = false)
	@DecimalMax(value="100.0")
	private double valorSecundarioNombres;
	@DecimalMin(value="0.0", inclusive = false)
	@DecimalMax(value="100.0")
	private double valorSecundarioApellidos;
	
	/**
	 * Constructor de la clase
	 * ConfigBusquedas
	 */
	public ConfigBusquedas() {
		super();
	}
	/**
	 * getLimiteCoincidenciaNombre
	 * @return double
	 */
	public double getLimiteCoincidenciaNombre() {
		return limiteCoincidenciaNombre;
	}
	/**
	 * setLimiteCoincidenciaNombre
	 * @param limiteCoincidenciaNombre - Limite de coincidencia Nombre
	 */
	public void setLimiteCoincidenciaNombre(double limiteCoincidenciaNombre) {
		this.limiteCoincidenciaNombre = limiteCoincidenciaNombre;
	}
	/**
	 * getPrecisionNombre
	 * @return double
	 */
	public double getPrecisionNombre() {
		return precisionNombre;
	}
	/**
	 * setPrecisionNombre
	 * @param precisionNombre - Precision de busqueda Cliente 360
	 */
	public void setPrecisionNombre(double precisionNombre) {
		this.precisionNombre = precisionNombre;
	}
	/**
	 * getValorPrimarioNombres
	 * @return double
	 */
	public double getValorPrimarioNombres() {
		return valorPrimarioNombres;
	}
	public void setValorPrimarioNombres(double valorPrimarioNombres) {
		this.valorPrimarioNombres = valorPrimarioNombres;
	}
	/**
	 * getValorPrimarioApellidos
	 * @return double
	 */
	public double getValorPrimarioApellidos() {
		return valorPrimarioApellidos;
	}
	public void setValorPrimarioApellidos(double valorPrimarioApellidos) {
		this.valorPrimarioApellidos = valorPrimarioApellidos;
	}
	/**
	 * getValorSecundarioNombres
	 * @return double
	 */
	public double getValorSecundarioNombres() {
		return valorSecundarioNombres;
	}
	public void setValorSecundarioNombres(double valorSecundarioNombres) {
		this.valorSecundarioNombres = valorSecundarioNombres;
	}
	/**
	 * getValorSecundarioApellidos
	 * @return double
	 */
	public double getValorSecundarioApellidos() {
		return valorSecundarioApellidos;
	}
	public void setValorSecundarioApellidos(double valorSecundarioApellidos) {
		this.valorSecundarioApellidos = valorSecundarioApellidos;
	}
}
