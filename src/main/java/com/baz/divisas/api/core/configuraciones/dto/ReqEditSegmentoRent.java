package com.baz.divisas.api.core.configuraciones.dto;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
public class ReqEditSegmentoRent {

	private BeanSegmentoRentabilidad segmentoRentabilidad = new BeanSegmentoRentabilidad();

	/**
	 * Constructor de la clase
	 */
	public ReqEditSegmentoRent() {
		super();
	}

	public BeanSegmentoRentabilidad getSegmentoRentabilidad() {
		return segmentoRentabilidad;
	}

	public void setSegmentoRentabilidad(BeanSegmentoRentabilidad segmentoRentabilidad) {
		this.segmentoRentabilidad = segmentoRentabilidad;
	}
}
