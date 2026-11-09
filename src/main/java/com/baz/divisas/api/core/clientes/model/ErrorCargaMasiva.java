package com.baz.divisas.api.core.clientes.model;

/**
 * Clase ErrorCargaMasiva
 * @author B813196
 * itoxtle
 */
public class ErrorCargaMasiva {

	private int linea;
	private String cadena;
	private String descripcion;
	
	/**
	 * Constructor de la clase
	 */
	public ErrorCargaMasiva() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param linea - Linea del error
	 * @param cadena - Cadena erronea
	 * @param descripcion - Descripcion Error
	 */
	public ErrorCargaMasiva(int linea, String cadena, String descripcion) {
		super();
		this.linea = linea;
		this.cadena = cadena;
		this.descripcion = descripcion;
	}
	
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public String getCadena() {
		return cadena;
	}
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
