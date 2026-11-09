package com.baz.divisas.api.core.clientes.model;

/**
 * Clase Identificacion
 * @author B813196
 * itoxtle
 */
public class Identificacion {
	
	private String tipo;
	private String folio;
	
	/**
	 * Constructor de la clase
	 */
	public Identificacion() {
		super();
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}

}
