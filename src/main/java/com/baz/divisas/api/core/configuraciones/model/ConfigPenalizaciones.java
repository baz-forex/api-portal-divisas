package com.baz.divisas.api.core.configuraciones.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * Clase ConfigPenalizaciones
 * @author B813196
 * itoxtle
 */
public class ConfigPenalizaciones {

	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	private double fechaNacimiento;
	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	private double desorden;
	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	private double parametroFaltante;
	
	/**
	 * Constructor de la clase
	 */
	public ConfigPenalizaciones() {
		super();
	}
	/**
	 * getFechaNacimiento
	 * @return double
	 */
	public double getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * setFechaNacimiento
	 * @param fechaNacimiento - Penalizacion de fecha de nacimiento Cliente 360
	 */
	public void setFechaNacimiento(double fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	/**
	 * getDesorden
	 * @return double
	 */
	public double getDesorden() {
		return desorden;
	}
	public void setDesorden(double desorden) {
		this.desorden = desorden;
	}
	/**
	 * getParametroFaltante
	 * @return double
	 */
	public double getParametroFaltante() {
		return parametroFaltante;
	}
	public void setParametroFaltante(double parametroFaltante) {
		this.parametroFaltante = parametroFaltante;
	}
}
