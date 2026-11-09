package com.baz.divisas.api.core.configuraciones.dto;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
public class ReqEditValoresRentabilidad {

	private BeanValoresRentabilidad valoresRentabilidad = new BeanValoresRentabilidad();

	/**
	 * Constructor de la clase
	 */
	public ReqEditValoresRentabilidad() {
		super();
	}

	public BeanValoresRentabilidad getValoresRentabilidad() {
		return valoresRentabilidad;
	}

	public void setValoresRentabilidad(BeanValoresRentabilidad valoresRentabilidad) {
		this.valoresRentabilidad = valoresRentabilidad;
	}
}
